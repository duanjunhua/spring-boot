package com.kingroad.pulsar.task;

import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.service.PulsarClusterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.Clusters;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 17:03
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Component
public class PulsarClusterStatusCheck {

    @Resource
    PulsarClusterService clusterService;

    // 5秒检查一次
    @Scheduled(fixedRate = 5000*60)
    public void checkStatus(){
        List<PulsarCluster> clusters = clusterService.findAll();
        if(ObjectUtils.isEmpty(clusters)) return;

        clusters.forEach(cluster -> {
            cluster.setStatus(clusterStatus(cluster.getAdminApiUrl(), cluster.getAuthPlugin(), cluster.getAuthParams()) ? PulsarCluster.Status.ACTIVE.name() : PulsarCluster.Status.INACTIVE.name());
        });
        clusterService.saveOrUpdateAll(clusters);
    }

    public Boolean clusterStatus(String serviceUrl, String authClass, String authParams){
        try {

            PulsarAdminBuilder pulsarAdminBuilder = PulsarAdmin.builder();

            pulsarAdminBuilder.connectionTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .requestTimeout(20, TimeUnit.SECONDS)
                    .serviceHttpUrl(serviceUrl);

            if(StringUtils.isNotBlank(authClass)){
                pulsarAdminBuilder.authentication(Class.forName(authClass).getName(), authParams);
            }

            Clusters clusters = pulsarAdminBuilder.build().clusters();
            if(CollectionUtils.isNotEmpty(clusters.getClusters())){
                return true;
            }
        }catch (Exception e){
            log.warn("集群不可达到：{}", serviceUrl);
        }
        return false;
    }

}

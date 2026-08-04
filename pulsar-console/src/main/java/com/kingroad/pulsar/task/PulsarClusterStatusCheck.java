package com.kingroad.pulsar.task;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kingroad.pulsar.common.CommonConst;
import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.service.PulsarClusterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.common.policies.data.ClusterData;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.pulsar.admin.authentication.param.token}")
    private String pulsarAdminUserToken;

    @Resource
    PulsarClusterService clusterService;

    // 5秒检查一次
    @Scheduled(fixedRate = 5000 * 60)
    public void saveCluster(){
        List<PulsarCluster> clusters = clusterService.findAll();
        if(ObjectUtils.isEmpty(clusters)) return;

        clusters.forEach(cluster -> {

            Boolean status = checkAndSaveClusterToMeta(cluster.getClusterName(), cluster.getServiceUrl(), cluster.getAdminApiUrl(), cluster.getAuthPlugin(), cluster.getAuthParams());

            cluster.setStatus(status ? PulsarCluster.Status.ACTIVE.name() : PulsarCluster.Status.INACTIVE.name());

        });

    }

    private Boolean checkAndSaveClusterToMeta(String clusterName, String brokerServiceUrl, String adminApiUrl, String authClass, String authParams){
        PulsarAdminBuilder pulsarAdminBuilder = PulsarAdmin.builder();

        try {
            pulsarAdminBuilder.connectionTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .requestTimeout(20, TimeUnit.SECONDS)
                    .serviceHttpUrl(adminApiUrl)
            ;

            if (StringUtils.isNotBlank(authClass)) {
                pulsarAdminBuilder.authentication(Class.forName(authClass).getName(), authParams);
            }

            PulsarAdmin plsAdmin = pulsarAdminBuilder.build();

            List<String> clusters = plsAdmin.clusters().getClusters();

            if(CollectionUtils.isEmpty(clusters)) return Boolean.FALSE;

            // 已存在对应集群
            if(clusters.contains(clusterName)){
                List<String> brokers = plsAdmin.brokers().getActiveBrokers(clusterName);
                return CollectionUtils.isNotEmpty(brokers) && CollectionUtils.size(brokers) > 0 ? Boolean.TRUE : Boolean.FALSE;
            }

            JSONObject object  = new JSONObject();
            object.put("token", pulsarAdminUserToken);

            PulsarAdmin superPlsAdmin = pulsarAdminBuilder.connectionTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .requestTimeout(20, TimeUnit.SECONDS)
                    .serviceHttpUrl(adminApiUrl)
                    .authentication(Class.forName(CommonConst.DEFAULT_PULSAR_ADMIN_AUTH_PLUGIN).getName(), JSONUtil.toJsonStr(object))
                    .build();


            // 集群参数：集群参数属性
            ClusterData clusterData = ClusterData.builder()
                    .serviceUrl(adminApiUrl)
                    .brokerServiceUrl(brokerServiceUrl)
                    .authenticationPlugin(authClass)
                    .authenticationParameters(authParams)
                    .build();

            // 异步新增集群
            superPlsAdmin.clusters().createClusterAsync(clusterName, clusterData);

            log.info("新增集群完成");
            List<String> brokers = plsAdmin.brokers().getActiveBrokers(clusterName);
            return CollectionUtils.isNotEmpty(brokers) && CollectionUtils.size(brokers) > 0 ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            log.error("集群不可达到或集群新增失败");
        }
        return Boolean.FALSE;
    }

}

package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.domain.vo.BrokerResourceVo;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.PulsarClusterRepository;
import com.kingroad.pulsar.util.PulsarUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.policies.data.NamespaceOwnershipStatus;
import org.apache.pulsar.policies.data.loadbalancer.LoadManagerReport;
import org.apache.pulsar.policies.data.loadbalancer.LoadReport;
import org.apache.pulsar.policies.data.loadbalancer.ResourceUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:09
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class PulsarClusterService {

    @Resource
    PulsarClusterRepository repository;

    /**
     * 分页查询
     */
    public PageResult<PulsarCluster> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<PulsarCluster> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public PulsarCluster saveOrUpdate(PulsarCluster entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public PulsarCluster findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<PulsarCluster> findAll(){
        return repository.findAll();
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public List<PulsarCluster> saveOrUpdateAll(List<PulsarCluster> entities) {
        return repository.saveAllAndFlush(entities);
    }

    public List<BrokerResourceVo> getClusterNodeResourceOverview(Long clusterId) throws PulsarAdminException {
        PulsarCluster cluster = repository.findById(clusterId).orElse(null);

        if(ObjectUtils.isEmpty(cluster)) return Collections.emptyList();

        PulsarAdmin admin = PulsarUtil.getAdmin(cluster);
        if(ObjectUtils.isEmpty(admin)) return Collections.emptyList();


        // 1. 获取集群所有在线Broker节点
        List<String> activeBrokers = admin.brokers().getActiveBrokers(cluster.getClusterName());

        if (activeBrokers.isEmpty()) {
            return Collections.emptyList();
        }

        // 遍历每个节点，组装资源数据
        List<BrokerResourceVo> result = new ArrayList<>();
        for (String brokerAddr : activeBrokers) {

            BrokerResourceVo.BrokerResourceVoBuilder builder = BrokerResourceVo.builder();
            builder.brokerAddr(brokerAddr);

            // 获取CPU、内存、网络带宽负载
            LoadManagerReport loadReport = admin.brokerStats().getLoadReport();
            builder.cpuUsage(loadReport.getCpu().percentUsage());
            builder.memoryUsage(loadReport.getMemory().percentUsage());

            //字节转MB
            builder.bandwidthIn(loadReport.getBandwidthIn().percentUsage() / 1024 / 1024);
            builder.bandwidthOut(loadReport.getBandwidthOut().percentUsage()  / 1024 / 1024);

            // 3. 获取业务运行统计指标：消息、连接、Topic业务统计
            BrokerStats stats = admin.brokerStats();

            String metrics = stats.getMetrics();

//            builder.producerCount(stats.getTopics())
//
//            vo.setProducerCount(stats.getProducers());
//            vo.setConsumerCount(stats.getConsumers());
//            vo.setTotalTopics(stats.getTopics());
//            vo.setMsgInRate(stats.getMsgInRate());
//
//          // 当前节点负责托管的所有命名空间
            Map<String, NamespaceOwnershipStatus> namespaces = admin.brokers().getOwnedNamespaces(cluster.getClusterName(), brokerAddr);
            builder.ownedNamespaces(namespaces);

            result.add(builder.build());
        }
        return result;

    }

}

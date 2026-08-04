package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.repository.PulsarClusterRepository;
import com.kingroad.pulsar.service.PulsarClusterService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:24
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/pulsar-clus")
public class PulsarClusterController extends BaseCrudController<PulsarCluster, Long, PulsarClusterRepository> {

    public PulsarClusterController(PulsarClusterRepository repository) {
        super(repository);
    }

    @Resource
    PulsarClusterService service;

    @PostMapping("/save-cluster")
    public Result<PulsarCluster> saveCluster(@Valid @RequestBody PulsarCluster entity) {
        if(ObjectUtils.isEmpty(entity.getId())) {
            return Result.success(service.saveOrUpdate(entity));
        }
        List<PulsarCluster> clusters = repository.findAll();
        clusters.forEach(cluster -> {
            if(cluster.getId() == entity.getId()) {
                BeanUtils.copyProperties(entity, cluster);
            }else {
                cluster.setIsDefault(!entity.getIsDefault());
            }
        });

        service.saveOrUpdateAll(clusters);
        return Result.success(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/set-default/{id}")
    public Result<PulsarCluster> setDefault(@PathVariable Long id) {
        Optional<PulsarCluster> optional = repository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("数据不存在，无法修改");
        }
        List<PulsarCluster> clusters = repository.findAll();
        clusters.forEach(cluster -> {
            if(cluster.getId() == id) {
                cluster.setIsDefault(true);
            }else {
                cluster.setIsDefault(false);
            }
        });

        service.saveOrUpdateAll(clusters);

        return Result.success(optional.get());
    }

    /**
     * 修改
     */
    @PostMapping("/change-status")
    public Result<PulsarCluster> changeStatus(Long id, String status) {
        Optional<PulsarCluster> optional = repository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("数据不存在，无法修改");
        }

        PulsarCluster cluster = optional.get();
        cluster.setStatus(status);
        repository.save(cluster);

        return Result.success(cluster);
    }

    /**
     * 查看集群在线运行状态、集群接入时间及关联节点资源概况。
     * @param clusterId
     * @return
     */
    @GetMapping("/info/{id}")
    public Result viewClusterInfo(@PathVariable("id") Long clusterId){
        // TODO:
        return Result.success();
    }
}

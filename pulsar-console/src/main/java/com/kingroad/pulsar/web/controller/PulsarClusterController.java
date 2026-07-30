package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.PulsarCluster;
import com.kingroad.pulsar.repository.PulsarClusterRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

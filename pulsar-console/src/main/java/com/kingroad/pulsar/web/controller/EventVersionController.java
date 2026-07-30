package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.EventVersion;
import com.kingroad.pulsar.repository.EventVersionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:22
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/event-version")
public class EventVersionController extends BaseCrudController<EventVersion, Long, EventVersionRepository> {

    public EventVersionController(EventVersionRepository repository) {
        super(repository);
    }

}

package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.EventDefinition;
import com.kingroad.pulsar.repository.EventDefinitionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:20
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/event-def")
public class EventDefinitionController extends BaseCrudController<EventDefinition, Long, EventDefinitionRepository> {

    public EventDefinitionController(EventDefinitionRepository repository) {
        super(repository);
    }

}

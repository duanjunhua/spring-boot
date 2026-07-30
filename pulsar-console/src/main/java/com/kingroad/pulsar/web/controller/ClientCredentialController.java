package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.ClientCredential;
import com.kingroad.pulsar.repository.ClientCredentialRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:19
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/client-creds")
public class ClientCredentialController extends BaseCrudController<ClientCredential, Long, ClientCredentialRepository> {

    public ClientCredentialController(ClientCredentialRepository repository) {
        super(repository);
    }

}

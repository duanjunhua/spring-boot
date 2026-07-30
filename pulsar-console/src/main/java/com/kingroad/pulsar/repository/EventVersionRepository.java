package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.EventVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:30
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface EventVersionRepository extends JpaRepository<EventVersion,Long> {
}

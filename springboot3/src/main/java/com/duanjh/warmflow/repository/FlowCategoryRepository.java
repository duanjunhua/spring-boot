package com.duanjh.warmflow.repository;

import com.duanjh.warmflow.jpa.FlowCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-20 周四 10:40
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface FlowCategoryRepository extends JpaRepository<FlowCategory, Long> {
}

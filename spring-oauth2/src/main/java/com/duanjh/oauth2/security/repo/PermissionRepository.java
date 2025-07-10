package com.duanjh.oauth2.security.repo;

import com.duanjh.oauth2.security.entity.Permission;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 16:41
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select p.id, p.name, p.resource, p.menu_id as menuId, p.state, p.expression, r.sn from t_login_role ur join t_role_permission rp on ur.role_id = rp.role_id "
                + " join t_permission p on p.id = rp.permission_id "
                + " join t_role r on r.id = rp.role_id "
                + " where ur.login_id = :userId ", nativeQuery = true)
    List<Tuple> findTupleByUserId(@Param("userId") Long userId);


    @Query(value = "select p.id, p.name, p.resource, p.menu_id as menuId, p.state, p.expression, r.sn from t_permission p join t_role_permission rp on p.id = rp.permission_id "
            + " left join t_role r on r.id = rp.role_id ", nativeQuery = true)
    List<Tuple> findTupleLst();

    default List<Permission> findPermissionsByUserId(Long userId){
        return findTupleByUserId(userId).stream().map(t -> new Permission(
            t.get("id", Long.class),
            t.get("name", String.class),
            t.get("state", Integer.class),
            t.get("resource", String.class),
            t.get("expression", String.class),
            t.get("menuId", Long.class),
            t.get("sn", String.class)
        )).toList();
    }

    default List<Permission> findPermissionLst(){
        return findTupleLst().stream().map(t -> new Permission(
            t.get("id", Long.class),
            t.get("name", String.class),
            t.get("state", Integer.class),
            t.get("resource", String.class),
            t.get("expression", String.class),
            t.get("menuId", Long.class),
            t.get("sn", String.class)
        )).toList();
    }
}

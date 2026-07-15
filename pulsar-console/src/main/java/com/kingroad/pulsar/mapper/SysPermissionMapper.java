package com.kingroad.pulsar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingroad.pulsar.entity.mr.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:51
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<String> selectPermsByRoleCodes(@Param("roleCodes") List<String> roleCodes);

    List<SysPermission> selectAllMenuTree();

}

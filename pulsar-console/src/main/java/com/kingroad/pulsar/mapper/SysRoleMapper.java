package com.kingroad.pulsar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingroad.pulsar.entity.mr.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:50
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleCodeByUserId(@Param("userId") Long userId);

}

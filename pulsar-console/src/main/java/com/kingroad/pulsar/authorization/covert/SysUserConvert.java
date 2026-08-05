package com.kingroad.pulsar.authorization.covert;

import com.kingroad.pulsar.domain.dto.SystemInitDto;
import com.kingroad.pulsar.domain.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-04 周二 11:08
 * @Version: v1.0
 * @Description: 使用mapstruct工具，自定义需要转换的对象映射关系，工具会自动实现接口
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser requestToEntity(SystemInitDto dto);

}

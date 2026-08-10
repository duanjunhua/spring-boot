package com.kingroad.pulsar.mapstruct;

import com.kingroad.pulsar.domain.dto.SsoUserApplyDto;
import com.kingroad.pulsar.domain.entity.SsoUserApply;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 15:03
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface SsoUserApplyConvert {

    SsoUserApplyConvert INSTANCE = Mappers.getMapper(SsoUserApplyConvert.class);

    SsoUserApply requestToEntity(SsoUserApplyDto dto);
}

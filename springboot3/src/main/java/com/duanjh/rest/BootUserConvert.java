package com.duanjh.rest;

import com.duanjh.jpa.entity.BootUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-12 周四 15:43
 * @Version: v1.0
 * @Description: 使用mapstruct工具，自定义需要转换的对象映射关系，工具会自动实现接口
 */
@Mapper // 导入mapstruct包
public interface BootUserConvert {

    BootUserConvert INSTANCE = Mappers.getMapper(BootUserConvert.class);

    BootUser requestToEntity(BootUserReq bsRequest);

    List<BootUserResp> entityToResponse(List<BootUser> bsList);

    BootUserResp entityToResponse(BootUser bs);

}

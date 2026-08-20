package com.duanjh.warmflow.convert;

import com.duanjh.warmflow.jpa.FlowCategory;
import org.dromara.warm.flow.core.dto.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-20 周四 10:54
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface FlowCateworyConvert {

    FlowCateworyConvert INSTANCE = Mappers.getMapper(FlowCateworyConvert.class);

    Tree categoryToTree(FlowCategory category);

    List<Tree> categoriesToTrees(List<FlowCategory> categories);

}

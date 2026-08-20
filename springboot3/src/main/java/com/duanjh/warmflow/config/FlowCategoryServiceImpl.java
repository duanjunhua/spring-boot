package com.duanjh.warmflow.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.duanjh.util.TreeUtil;
import com.duanjh.warmflow.convert.FlowCateworyConvert;
import com.duanjh.warmflow.jpa.FlowCategory;
import com.duanjh.warmflow.repository.FlowCategoryRepository;
import jakarta.annotation.Resource;
import org.dromara.warm.flow.core.dto.Tree;
import org.dromara.warm.flow.ui.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-20 周四 10:34
 * @Version: v1.0
 * @Description: 实现流程类别自定义查询
 */
@Service
public class FlowCategoryServiceImpl implements CategoryService {

    @Resource
    FlowCategoryRepository repository;

    @Override
    public List<Tree> queryCategory() {

        List<FlowCategory> all = repository.findAll();

        if(CollectionUtils.isEmpty(all)){
            return List.of();
        }

        List<FlowCategory> categories = TreeUtil.buildTree(
                all,
                FlowCategory::getId,
                FlowCategory::getParentId,
                FlowCategory::getChildren,
                (parent, childList) -> parent.setChildren(childList),
                null
        );

        return FlowCateworyConvert.INSTANCE.categoriesToTrees(categories);
    }
}

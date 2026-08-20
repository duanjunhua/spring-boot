package com.duanjh.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-07 周五 09:54
 * @Version: v1.0
 * @Description: 树形工具
 */
public class TreeUtil {

    /**
     * 构建树形结构
     * @param list 原始数据列表
     * @param getId 获取当前节点唯一ID
     * @param getParentId 获取父节点ID
     * @param setChildren 设置子节点集合的方法
     * @param rootParentId 根节点父ID（通常为null、0、-1）
     * @return 根节点树形集合
     */
    public static <T, ID> List<T> buildTree(List<T> list, Function<T, ID> getId, Function<T, ID> getParentId, BiConsumer<T, List<T>> setChildren, ID rootParentId) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        // 1. 建立ID -> 实体映射
        Map<ID, T> nodeMap = list.stream().collect(Collectors.toMap(getId, Function.identity(), (k1, k2) -> k1));

        List<T> rootList = new ArrayList<>();

        for (T node : list) {
            ID parentId = getParentId.apply(node);
            // 判断是否根节点
            if (Objects.equals(parentId, rootParentId)) {
                rootList.add(node);
            } else {
                // 找到父节点，加入子节点
                T parentNode = nodeMap.get(parentId);
                if (parentNode != null) {
                    // 获取父节点现有子节点，不存在则新建
                    List<T> children = new ArrayList<>();
                    // 先尝试取出原有children（可选优化，如果VO自带children）
                    // 这里统一重新组装
                    setChildren.accept(parentNode, children);
                    children.add(node);
                }
            }
        }
        return rootList;
    }

    /**
     * @param list 原始数据
     * @param getId 获取ID
     * @param getParentId 获取父ID
     * @param getChildren 获取子节点集合（用于判断是否已有集合）
     * @param setChildren 设置子节点
     * @param rootParentId 根父ID
     * @param comparator 同层级排序规则，传null不排序
     */
    public static <T, ID> List<T> buildTree(List<T> list, Function<T, ID> getId, Function<T, ID> getParentId, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren, ID rootParentId, Comparator<T> comparator) {

        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        Map<ID, T> nodeMap = list.stream().collect(Collectors.toMap(getId, Function.identity(), (o1, o2) -> o1));
        List<T> roots = new ArrayList<>();

        for (T item : list) {
            ID pid = getParentId.apply(item);
            if (Objects.equals(pid, rootParentId)) {
                roots.add(item);
            } else {
                T parent = nodeMap.get(pid);
                if (parent != null) {
                    List<T> children = getChildren.apply(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        setChildren.accept(parent, children);
                    }
                    children.add(item);
                }
            }
        }

        // 层级排序
        if (comparator != null) {
            sortRecursive(roots, getChildren, comparator);
        }

        return roots;
    }

    /**
     * 递归排序所有层级
     */
    private static <T> void sortRecursive(List<T> nodes, Function<T, List<T>> getChildren, Comparator<T> comparator) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        nodes.sort(comparator);
        for (T node : nodes) {
            List<T> children = getChildren.apply(node);
            sortRecursive(children, getChildren, comparator);
        }
    }

    /**
     * 构建树形结构
     * @param list 原始数据列表
     * @param getId 获取当前节点唯一ID
     * @param getParentId 获取父节点ID
     * @param setChildren 设置子节点集合的方法
     * @param rootParentId 根节点父ID（通常为null、0、-1）
     * @return 根节点树形集合
     */
    public static <T, ID> List<T> buildTree(List<T> list,  Function<T, ID> getId, Function<T, ID> getParentId, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren, ID rootParentId) {
        return buildTree(list, getId, getParentId, getChildren, setChildren, rootParentId, null);
    }


    /**
     * 递归扁平化树形结构（树 → 平铺列表）
     * @param treeNodes 根节点树
     * @param getChildren 获取子节点方法
     * @return 扁平化后的所有节点
     */
    public static <T> List<T> flatTree(List<T> treeNodes, Function<T, List<T>> getChildren) {
        List<T> result = new ArrayList<>();
        if (treeNodes == null || treeNodes.isEmpty()) {
            return result;
        }
        for (T node : treeNodes) {
            result.add(node);
            List<T> children = getChildren.apply(node);
            if (children != null && !children.isEmpty()) {
                result.addAll(flatTree(children, getChildren));
            }
        }
        return result;
    }

    public interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}

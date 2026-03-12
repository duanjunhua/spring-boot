package com.duanjh.dynamicds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-10 周二 17:32
 * @Version: v1.0
 * @Description: 动态数据源
 */
public class DynamicDs extends AbstractRoutingDataSource {

    /**
     * ThreadLocal 用于提供线程局部变量，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。
     */
    private static final ThreadLocal<String> DS_HOLDER = new ThreadLocal<>();

    public DynamicDs(DataSource defaultDs, Map<Object, Object> targetDs) {
        //默认目标数据源
        super.setDefaultTargetDataSource(defaultDs);
        //目标数据源集合。数据源切换时从此列表选择
        super.setTargetDataSources(targetDs);
        //属性设置
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // 更具数据源key。获取选择的数据源。
        return getDataSource();
    }

    public static void setDataSource(String dataSource) {
        DS_HOLDER.set(dataSource);
    }

    public static String getDataSource() {
        return DS_HOLDER.get();
    }

    public static void clearDataSource() {
        DS_HOLDER.remove();
    }
}

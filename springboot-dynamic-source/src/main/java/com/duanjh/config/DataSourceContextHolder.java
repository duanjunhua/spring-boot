package com.duanjh.config;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-18 周一 17:50
 * @Version: v1.0
 * @Description: 提供线程局部变量。这些变量不同于它们的正常对应关系是每个线程访问一个线程(通过get、set方法),有自己的独立初始化变量的副本
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> DATASOURCE_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dataSourceName 数据源名称
     */
    public static void setDataSource(String dataSourceName) {
        DATASOURCE_HOLDER.set(dataSourceName);
    }

    /**
     * 获取当前线程的数据源
     */
    public static String getDataSource(){
        return DATASOURCE_HOLDER.get();
    }

    public static void clear(){
        DATASOURCE_HOLDER.remove();
    }
}

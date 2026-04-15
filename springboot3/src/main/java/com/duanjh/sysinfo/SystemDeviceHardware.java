package com.duanjh.sysinfo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-09 周四 17:09
 * @Version: v1.0
 * @Description: 系统硬件信息
 */
@Data
public class SystemDeviceHardware {

    /**
     * 操作系统信息
     */
    private Os os;

    /**
     * CPU信息
     */
    private Cpu cpu;

    /**
     * 内存信息
     */
    private Memory memory;

    /**
     * 磁盘信息（多个挂载点）
     */
    private List<Disk> disks;

    /**
     * 网络信息（多个网卡）
     */
    private List<Network> networks;

    /**
     * 操作系统
     */
    @Data
    public static class Os {

        /**
         * 系统名称（如Windows 11、CentOS 8）
         */
        private String name;

        /**
         * 系统版本
         */
        private String version;

        /**
         * 系统运行时间（秒）
         */
        private long uptime;

    }

    /**
     * CPU
     */
    @Data
    public static class Cpu {

        /**
         * CPU型号
         */
        private String model;

        /**
         * 物理核心数
         */
        private int physicalCores;

        /**
         * 逻辑核心数
         */
        private int logicalCores;

        /**
         * CPU使用率（%）
         */
        private double usage;
    }

    /**
     * 内存
     */
    @Data
    public static class Memory {

        /**
         * 总内存
         */
        private String total;

        /**
         * 可用内存
         */
        private String available;

        /**
         * 已用内存
         */
        private String used;

        /**
         * 内存使用率（%）
         */
        private double usage;
    }

    /**
     * 磁盘
     */
    @Data
    public static class Disk {

        /**
         * 挂载点（如C:\、/root）
         */
        private String mountPoint;

        /**
         * 总容量
         */
        private String total;

        /**
         * 已用容量
         */
        private String used;

        /**
         * 磁盘使用率（%）
         */
        private double usage;
    }

    /**
     * 网络
     */
    @Data
    public static class Network {

        /**
         * 网卡名称（如eth0、WLAN）
         */
        private String name;

        /**
         * MAC地址
         */
        private String mac;

        /**
         * 接收字节数
         */
        private String recvBytes;

        /**
         * 发送字节数
         */
        private String sendBytes;
    }
}

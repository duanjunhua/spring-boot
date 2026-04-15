package com.duanjh.sysinfo;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-09 周四 17:06
 * @Version: v1.0
 * @Description: 系统硬件信息采集工具类
 */
public class SystemDeviceInfoUtil {

    // 单例，避免频繁创建SystemInfo对象
    private static final SystemInfo INSTANCE = new SystemInfo();

    /**
     * 私有构造，禁止外部实例化
     */
    private SystemDeviceInfoUtil() {

    }

    /**
     * 获取操作系统信息
     */
    public static OperatingSystem getOsInfo() {
        return INSTANCE.getOperatingSystem();
    }

    /**
     * 获取CPU信息
     */
    public static CentralProcessor getCpuInfo() {
        return INSTANCE.getHardware().getProcessor();
    }

    /**
     * 获取内存信息
     */
    public static GlobalMemory getMemoryInfo() {
        return INSTANCE.getHardware().getMemory();
    }

    /**
     * 获取磁盘信息（所有挂载点）
     */
    public static List<OSFileStore> getDiskInfo() {
        return getOsInfo().getFileSystem().getFileStores();
    }

    /**
     * 获取网络接口信息
     */
    public static List<NetworkIF> getNetworkInfo() {
        return INSTANCE.getHardware().getNetworkIFs();
    }

    /**
     * 格式化字节数（B→KB→MB→GB）
     */
    public static String formatBytes(long bytes) {
        return FormatUtil.formatBytes(bytes);
    }
}

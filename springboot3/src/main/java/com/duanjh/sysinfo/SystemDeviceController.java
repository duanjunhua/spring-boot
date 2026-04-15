package com.duanjh.sysinfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 13:55
 * @Version: v1.0
 * @Description: 获取设备信息
 */
@RestController
@RequestMapping("/sys-device")
public class SystemDeviceController {

    @GetMapping("/os")
    public OperatingSystem getOperatingSystem() {
        return SystemDeviceInfoUtil.getOsInfo();
    }

    @GetMapping("/cpu")
    public CentralProcessor getCpu() {
        return SystemDeviceInfoUtil.getCpuInfo();
    }

    @GetMapping("/memory")
    public GlobalMemory getMemory() {
        return SystemDeviceInfoUtil.getMemoryInfo();
    }

    @GetMapping("/disk")
    public List<OSFileStore> getDisk() {
        return SystemDeviceInfoUtil.getDiskInfo();
    }

    @GetMapping("/network")
    public List<NetworkIF> getNetwork() {
        return SystemDeviceInfoUtil.getNetworkInfo();
    }

}

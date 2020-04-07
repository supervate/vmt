package vt.vmt.status.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;
import vt.vmt.status.entity.DiskInfo;
import vt.vmt.status.entity.SysInfo;
import vt.vmt.status.entity.SysInfo.SysInfoBuilder;
import vt.vmt.status.entity.SysStaticInfo;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * get the static system info of machine
 *
 * @author vate
 * date: 2020/3/25 15:54
 */
@Service
public class SysInfoService {

    public SysInfo getSysInfos() {
        return collectSysInfo();
    }

    private static SysInfo collectSysInfo() {
        final SysInfoBuilder builder = SysInfo.builder();
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存
        long memSize = osmxb.getTotalPhysicalMemorySize();
        // 剩余的物理内存
        long memFreeSize = osmxb.getFreePhysicalMemorySize();
        // 已使用的物理内存
        long usedMem = memSize - memFreeSize;

        builder.memSize(memSize);
        builder.memUsedByAll(usedMem);
        builder.memUsedByApp(SystemUtil.getTotalMemory());

        // 磁盘使用情况
        List<DiskInfo> diskInfos = new ArrayList<>();
        File[] files = File.listRoots();
        for (File file : files) {
            diskInfos.add(DiskInfo.builder()
                    .total(file.getTotalSpace())
                    .free(file.getUsableSpace())
                    .name(StrUtil.isBlank(file.getName())? file.getPath() : file.getName())
                    .build());
        }
        builder.diskInfos(diskInfos);
        builder.sysStaticInfo(SysStaticInfo.SYS_STATIC_INFO_INSTANCE);

        return builder.build();
    }
}

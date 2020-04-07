package vt.vmt.status.entity;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * date: 2020/4/1 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysStaticInfo {
    public static final SysStaticInfo SYS_STATIC_INFO_INSTANCE = new SysStaticInfo();
    static {
        final OsInfo osInfo = SystemUtil.getOsInfo();
        SYS_STATIC_INFO_INSTANCE.setOsName(osInfo.getName());
        SYS_STATIC_INFO_INSTANCE.setOsArch(osInfo.getArch());
        SYS_STATIC_INFO_INSTANCE.setLineSeparator(osInfo.getLineSeparator());
        SYS_STATIC_INFO_INSTANCE.setFileSeparator(osInfo.getFileSeparator());
        SYS_STATIC_INFO_INSTANCE.setPid(String.valueOf(SystemUtil.getCurrentPID()));
    }
    String osName;
    String osArch;
    String lineSeparator;
    String fileSeparator;
    String pid;
}

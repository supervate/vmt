package vt.vmt.status.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * date: 2020/3/25 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysInfo {

    /**
     * the os static info
     */
    SysStaticInfo sysStaticInfo = SysStaticInfo.SYS_STATIC_INFO_INSTANCE;
    /**
     * the memory total size of currents machine
     */
    Long memSize;
    /**
     * the memory size of used.
     */
    Long memUsedByAll;
    /**
     * the memory size of currents app used.
     */
    Long memUsedByApp;
    /**
     * all disk info
     */
    List<DiskInfo> diskInfos;
    /**
     * current timeStamp
     */
    String timeStamp;
    //...and other,waits to add

}

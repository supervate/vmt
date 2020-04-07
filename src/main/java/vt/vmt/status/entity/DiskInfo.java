package vt.vmt.status.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * date: 2020/3/25 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiskInfo {
    String name;
    long total;
    long free;
}

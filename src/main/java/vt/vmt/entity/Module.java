package vt.vmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * date: 2020/4/6 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Module {
    int order;
    String name;
    String indexUri;
}

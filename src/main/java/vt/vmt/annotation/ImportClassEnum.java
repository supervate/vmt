package vt.vmt.annotation;

import org.springframework.context.annotation.ImportSelector;
import vt.vmt.log.config.VmtLogConfiguration;
import vt.vmt.log.web.LogController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * @date 2020/2/23 22:53
 */
public enum ImportClassEnum {
    /**
     * log components
     */
    LOG(Arrays.asList(
            VmtLogConfiguration.class.getName(),
            LogController.class.getName()
    )),
    /**
     * thread components
     */
    THREAD(Collections.emptyList());

    private List<String> clazzNames;

    private ImportClassEnum(List<String> clazzNames) {
        this.clazzNames = clazzNames;
    }

    public List<String> getClazzNames() {
        return clazzNames;
    }
}

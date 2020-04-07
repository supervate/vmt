package vt.vmt.annotation;

import vt.vmt.log.config.VmtLogConfiguration;
import vt.vmt.log.web.LogIndexController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author vate
 */
public enum ImportClassEnum {
    /**
     * log components
     */
    LOG(Arrays.asList(
            VmtLogConfiguration.class.getName(),
            LogIndexController.class.getName()
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

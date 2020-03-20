package vt.vmt.log.config;

import cn.hutool.core.lang.Console;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static vt.vmt.VmtConstant.*;
/**
 * @author vate
 */
@ConfigurationProperties(prefix = "vmt.log")
@Configuration
public class VmtLogConfiguration {
    /**
     * the log files root dir,contains all log files.
     */
    @Getter
    @Setter
    private String dir;
    /**
     * the max line number of per log preview request.
     */
    @Getter
    @Setter
    private Integer previewSize;

    public void setDir(String dir) {
        // the dir path must be end with '/'
        if (!dir.endsWith(SUFFIX_SLASH) && !dir.endsWith(SUFFIX_BACKSLASH)){
            dir += SUFFIX_SLASH;
        }
        this.dir = dir;
    }

    public void setPreviewSize(Integer previewSize) {
        if (previewSize <= 0 ){
            previewSize = LOG_PREVIEW_DEFAULT_MAX_LINE;
        }
        this.previewSize = previewSize;
    }

    public static void main(String[] args) {
        Console.log(Double.toString(12.3123213));
    }
}

package vt.vmt.status.config;

import cn.hutool.core.lang.Console;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 */
@ConfigurationProperties(prefix = "vmt.status")
@Configuration
public class VmtStatusConfiguration {

}

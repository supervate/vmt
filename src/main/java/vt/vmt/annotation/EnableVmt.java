package vt.vmt.annotation;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vate
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableConfigurationProperties
@Import(VmtImportBeanDefinitionRegistrar.class)
public @interface EnableVmt {
    boolean openLogs() default false;
    boolean openStatus() default false;
}

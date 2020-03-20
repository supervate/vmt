package vt.vmt.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static vt.vmt.VmtConstant.*;

public class VmtImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerAllImport(Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableVmt.class.getName())), registry);
    }

    /**
     * determine what need be load according config.
     * @param annotationAttributes
     * @param registry
     * @return
     */
    private String[] registerAllImport(Map<String, Object> annotationAttributes, BeanDefinitionRegistry registry){
        List<String> imports = new ArrayList<>();
        VmtClassPathBeanDefinitionScanner scanner = new VmtClassPathBeanDefinitionScanner(registry,true);
        // it will load the log module's classes when call doScan.
        if ((boolean) annotationAttributes.get("openLogs")) {
            scanner.doScan(ROOT_PACKAGE_LOG);
        }
        return imports.toArray(new String[0]);
    }
}

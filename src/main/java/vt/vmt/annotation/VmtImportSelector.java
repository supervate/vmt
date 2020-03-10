package vt.vmt.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static vt.vmt.VmtConstant.ROOT_PACKAGE_LOG;

public class VmtImportSelector implements ImportSelector {


    private String[] resolveAllImport(Map<String, Object> annotationAttributes){
        List<String> imports = new ArrayList<>();
        if ((boolean) annotationAttributes.get("openLogs")) {
            imports.addAll(ImportClassEnum.LOG.getClazzNames());
        }
        return imports.toArray(new String[0]);
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return resolveAllImport(Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableVmt.class.getName())));
    }
}

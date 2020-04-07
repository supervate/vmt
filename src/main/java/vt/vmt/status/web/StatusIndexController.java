package vt.vmt.status.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vt.vmt.entity.Module;
import vt.vmt.index.IndexController;
import vt.vmt.status.config.VmtStatusConfiguration;
import vt.vmt.status.service.SysInfoService;

import javax.annotation.PostConstruct;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 */
@Controller
@Slf4j
@RequestMapping("/" + URI_PREFIX_VMT + "/" + MODULE_NAME_STATUS)
public class StatusIndexController{
    @Autowired
    VmtStatusConfiguration vmtStatusConfiguration;
    @Autowired
    SysInfoService sysInfoService;

    @PostConstruct
    public void init(){
        IndexController.registModule(
                Module.builder()
                        .name(MODULE_NAME_STATUS)
                        .indexUri(URI_PREFIX_VMT + "/" + MODULE_NAME_STATUS)
                        .order(0)
                        .build());
    }

    @GetMapping
    public String statusPage(){
       return "vmt/status/statusIndex.html";
    }
}
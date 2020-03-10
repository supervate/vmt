package vt.vmt.log.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vt.vmt.log.config.VmtLogConfiguration;
import vt.vmt.log.util.CompressUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * @date 2020/1/7 11:38
 */
@Controller
@Slf4j
@RequestMapping(API_PREFIX_PATH + "/log")
public class LogController {

    @Autowired
    VmtLogConfiguration logConfiguration;

    @RequestMapping
    public String logPage(HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
        return "/vmt/log/logIndex";
    }

    @GetMapping("list")
    @ResponseBody
    public List<Map> listLogFiles(){
        File logDir = new File(logConfiguration.getDir());
        File[] files = logDir.listFiles();
        List<Map> logFiles = new ArrayList();
        for (File file : files) {
            if (file.getName().endsWith(".log")){
                Map logInfo = new HashMap();
                logInfo.put("fileName",file.getName());
                logInfo.put("fileSize",file.length()/(1024) + "kb");
                logFiles.add(logInfo);
            }
        }
        return logFiles;
    }

    @RequestMapping("download/all")
    public void downloadAllLogFiles(HttpServletResponse response){
        log.info("日志文件: 开始下载(全部)...");
        File logDir = new File(logConfiguration.getDir());
        File[] files = logDir.listFiles();
        List<String> fileNames = Arrays.stream(files).map(file -> file.getName()).collect(Collectors.toList());
        CompressUtil.downloadZipArchive(logConfiguration.getDir(),fileNames,"logs",response);
        log.info("日志文件: 下载完成!");
    }

    @RequestMapping("download/some")
    public void downloadLogFiles(@RequestParam String fileNameBase64,@RequestParam(defaultValue = "false") boolean rawFile, HttpServletResponse response) throws IOException {
        log.info("日志文件: 开始下载(部分)...");
        if (StrUtil.isBlank(fileNameBase64)){
            log.warn("日志文件: 下载文件名参数为空.");
            return;
        }
        String fileNames = Base64.decodeStr(fileNameBase64);
        List<String> fileNameArr = Arrays.asList(fileNames.split(","));
        if (fileNameArr.size() == 1 && rawFile){
            try (
                    OutputStream outputStream = CompressUtil.customizeResponse(response, fileNameArr.get(0));
            ) {
                Files.copy(Paths.get(logConfiguration.getDir(), fileNameArr.get(0)), outputStream);
            }
        }else {
            CompressUtil.downloadZipArchive(logConfiguration.getDir(),fileNameArr,"logs",response);
        }
        log.info("日志文件: 下载完成!");
    }
}
package vt.vmt.log.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vt.vmt.log.config.VmtLogConfiguration;
import vt.vmt.log.util.CompressUtil;
import vt.vmt.log.util.LogUtil;

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
 */
@Controller
@Slf4j
@RequestMapping(API_PREFIX_PATH + "/log")
public class LogController {

    @Autowired
    VmtLogConfiguration logConfiguration;

    @RequestMapping
    public String logPage(HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
        return "vmt/log/logIndex";
    }

    @GetMapping("list")
    @ResponseBody
    public List<Map> listLogFiles(){
        File logDir = new File(logConfiguration.getDir());
        File[] files = logDir.listFiles();
        List<Map> logFiles = new ArrayList<>();
        for (File file : files != null ? files : new File[0]) {
            if (file.getName().endsWith(".log")){
                Map logInfo = new HashMap();
                logInfo.put("fileName",file.getName());
//                float fileLength = file.length();
//                if (fileLength > BYTE_SIZE_MB){
//                    logInfo.put("fileSize", String.format("%.2f mb", fileLength /BYTE_SIZE_MB ));
//                }else {
//                    logInfo.put("fileSize", String.format("%.2f kb", fileLength /BYTE_SIZE_KB ));
//                }
                logInfo.put("fileSize", file.length());
                logFiles.add(logInfo);
            }
        }
        return logFiles;
    }

    @RequestMapping("download/all")
    public void downloadAllLogFiles(HttpServletResponse response){
        log.debug("log: downloading(all logs)...");
        File logDir = new File(logConfiguration.getDir());
        File[] files = logDir.listFiles();
        List<String> fileNames = Arrays.stream(files).map(file -> file.getName()).collect(Collectors.toList());
        CompressUtil.downloadZipArchive(logConfiguration.getDir(),fileNames,"logs",response);
        log.debug("log: download success.");
    }

    @RequestMapping("download/some")
    public void downloadLogFiles(@RequestParam String fileNameBase64,@RequestParam(defaultValue = "false") boolean rawFile, HttpServletResponse response) throws IOException {
        log.debug("log: downloading(part)...");
        if (StrUtil.isBlank(fileNameBase64)){
            log.warn("log: the fileName parameter is empty.");
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
        log.debug("log: download success.");
    }

    @RequestMapping("previewPage/{fileNameBase64}")
    public String preview(ModelMap modelMap, @PathVariable(name = "fileNameBase64") String fileNameBase64) {
        String logName = Base64.decodeStr(fileNameBase64);
        modelMap.put("logName",logName);
        return "vmt/log/logPreview";
    }

    @RequestMapping("preview/{fileNameBase64}")
    public void preview(HttpServletResponse response,@PathVariable(name = "fileNameBase64") String fileNameBase64,@RequestParam(defaultValue = LOG_PREVIEW_DEFAULT_START_LINE + "") int startLine) throws IOException {
        log.debug("log: reading content from log files...");
        String logName = Base64.decodeStr(fileNameBase64);
        File logFile = new File(logConfiguration.getDir() + logName);
        if (logFile.exists()){
            LogUtil.responseText(logFile,startLine,response,logConfiguration.getPreviewSize());
        }else {
            LogUtil.responseText(String.format("This file is not exists: %s", logName),response);
        }
        log.debug("log: log content transform success.");
    }
}
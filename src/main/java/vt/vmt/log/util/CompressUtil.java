package vt.vmt.log.util;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class CompressUtil {

    private static final int BUFFER_SIZE = 1024 * 1024;

    /**
     * 下载ZIP格式压缩包
     *
     * @param dirPath   存放文件的目录所在路径
     * @param fileNames 名称列表
     * @param response  响应体
     */
    public static void downloadZipArchive(String dirPath, List<String> fileNames, String zipName, HttpServletResponse response) {
        try (
                OutputStream out = customizeResponse(response, zipName, "zip");
                ZipOutputStream zipOut = new ZipOutputStream(out)
        ) {
            for (String fileName : fileNames) {
                // 文件夹路径
                String filePath = dirPath + fileName;
                byte[] data = Files.readAllBytes(new File(filePath).toPath());
                Path path = Paths.get(filePath);
                // 命名ZIP文件，打包时移除目录所在路径
                zipOut.putNextEntry(new ZipEntry(path.toFile().getName()));
                customizeOutputStream(zipOut, data, BUFFER_SIZE);
                zipOut.closeEntry();
            }
            zipOut.flush();
        } catch (IOException e) {
            log.error(" ========= 文件下载失败 =========", e);
        }
    }

    /**
     * 配置response实例
     *
     * @param response 响应体
     * @param fileName 文件名称
     * @return OutputStream
     */
    public static OutputStream customizeResponse(HttpServletResponse response, String fileName)
            throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        return response.getOutputStream();
    }

    /**
     * 配置response实例
     *
     * @param response 响应体
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return OutputStream
     * @throws IOException
     */
    public static OutputStream customizeResponse(HttpServletResponse response, String fileName,
                                                 String fileType)
            throws IOException {
        if ("zip".equals(fileType)){
            response.setContentType("application/x-zip-compressed");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + fileType, "UTF-8"));
        return response.getOutputStream();
    }

    /**
     * 读取字节数据，存入输出流outputStream
     *
     * @param out          输出流
     * @param data         希望写入的数据
     * @param bufferLength 写入数据是循环读取写入的，此为每次读取的字节长度
     * @throws IOException
     */
    public static void customizeOutputStream(OutputStream out, byte[] data, int bufferLength)
            throws IOException {
        int readTimes = data.length % bufferLength;
        int count = readTimes == 0 ? data.length / bufferLength : data.length / bufferLength + 1;
        for (int countIndex = 0; countIndex < count; countIndex++) {
            if (countIndex == count - 1 && readTimes != 0) {
                out.write(data, countIndex * bufferLength, readTimes);
                continue;
            }
            out.write(data, countIndex * bufferLength, bufferLength);
        }
    }

}
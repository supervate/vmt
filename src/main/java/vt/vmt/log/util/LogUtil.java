package vt.vmt.log.util;

import cn.hutool.core.io.IoUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 */
public class LogUtil {

    public static void responseText(String content, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(content);
        response.flushBuffer();
    }

    public static void responseText(File logFile, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (
                InputStream is = new FileInputStream(logFile);
                OutputStream os = response.getOutputStream();
        ) {
            IoUtil.copy(is, os);
            os.flush();
        }
    }

    public static void responseText(File logFile, int startLine, HttpServletResponse response, int previewSize) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (
                PrintWriter writer = response.getWriter();
                LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(logFile));
                LineNumberReader lineNumberReader2 = new LineNumberReader(new FileReader(logFile))
        ) {
            int logLineTotal = 0;
            while (lineNumberReader.readLine() != null){
                logLineTotal++;
            }
            int start = startLine,end;
            // read the recently data of previewSize
            if (logLineTotal <= 0){
//                responseText("There hasn't content now.",response);
                return;
            }
            if (start >= logLineTotal){
                response.setHeader("start-line", String.valueOf(startLine));
                return;
            } else if (LOG_PREVIEW_DEFAULT_START_LINE == startLine){
                start = logLineTotal > previewSize ? logLineTotal - previewSize : 0;
            }
            end = Math.min(start + previewSize - 1, logLineTotal);
            response.setHeader("start-line", String.valueOf(end+1));

            int currentLineNumber = 0;
            String currentLine;
            while ((currentLine = lineNumberReader2.readLine()) != null){
                if (++currentLineNumber >= start){
                    if (currentLineNumber <= end){
                        writer.println(currentLine + "<br/>");
                    }else {
                        break;
                    }
                }
            }
        }
    }
}

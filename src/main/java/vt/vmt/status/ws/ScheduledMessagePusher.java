package vt.vmt.status.ws;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vt.vmt.status.entity.SysInfo;
import vt.vmt.status.service.SysInfoService;

import java.time.Instant;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author vate
 * date: 2020/3/25 17:49
 */
@Component
@Slf4j
public class ScheduledMessagePusher {

    public static final int STATUS_WS_SYSINFO_SEND_PERIOD = 3000;

    private static boolean inited = false;
    private static final Object lock = new Object();

    @Autowired
    SysInfoService sysInfoService;

    private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @EventListener(ApplicationStartedEvent.class)
    public void start() {
        synchronized (lock) {
            if (!inited) {
                scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    try {
                        this.pushMessage();
                    } catch (Throwable t) {
                        log.error("vmt-status-ws: Found error when push sysInfo to client.", t);
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
                inited = true;
            }
        }
    }

    private void pushMessage() {
        Set<Entry<String, VmtWebSocketServer>> entrySet = VmtWebSocketServer.getWebSocketMap().entrySet();
        if (CollectionUtil.isNotEmpty(entrySet)) {
            ResponseEntity<SysInfo> responseEntity = ResponseEntity.ok(sysInfoService.getSysInfos());
            for (Entry<String, VmtWebSocketServer> entry : entrySet) {
                try {
                    VmtWebSocketServer ws = entry.getValue();
                    long now = Instant.now().toEpochMilli();
                    long pre = ws.getPreSendTimestamp();
                    if (pre == -1 || now - pre > STATUS_WS_SYSINFO_SEND_PERIOD) {
                        ws.sendMessage(JSONUtil.toJsonStr(responseEntity));
                        ws.setPreSendTimestamp(now);
                    }
                } catch (Throwable t) {
                    log.error("vmt-status-ws: Found error when push sysInfo to client.", t);
                }
            }
        }
    }
}

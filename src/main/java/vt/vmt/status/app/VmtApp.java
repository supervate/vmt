package vt.vmt.status.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vt.vmt.annotation.EnableVmt;

@SpringBootApplication
@EnableVmt(openStatus = true,openLogs = true)
public class VmtApp {
    public static void main(String[] args) {
        SpringApplication.run(VmtApp.class, args);
    }
}

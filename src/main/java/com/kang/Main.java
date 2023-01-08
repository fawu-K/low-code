package com.kang;

import com.kang.database.EnableAutoDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fawu.K
 */
@SpringBootApplication
@EnableAutoDB
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

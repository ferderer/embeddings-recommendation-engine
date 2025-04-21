package de.ferderer.ere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class,
})
public class App {

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}

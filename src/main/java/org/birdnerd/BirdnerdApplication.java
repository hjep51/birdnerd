package org.birdnerd;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BirdnerdApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(BirdnerdApplication.class, args);
    }
}

package org.birdnerd;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.birdnerd.data.SamplePersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@Theme(value = "my-app")
public class BirdnerdApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(BirdnerdApplication.class, args);
    }

}

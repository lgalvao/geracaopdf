package geracaopdf;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class GeracaoPdfApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeracaoPdfApplication.class, args);
    }

    @Bean
    Faker faker() {
        return Faker.instance();
    }
}

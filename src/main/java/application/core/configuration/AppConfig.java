package application.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean
    public HttpClient logHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    @Bean(name = "consoleOutput", destroyMethod = "")
    public PrintStream consoleOutput() {
        return System.out;
    }
}

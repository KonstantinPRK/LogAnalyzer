package application.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Создаёт инфраструктурные зависимости приложения.
 */
@Configuration
public class AppConfig {
    /**
     * Создает конфигурацию инфраструктурных компонентов.
     */
    public AppConfig() {
    }


    /**
     * Создаёт HTTP-клиент для загрузки удалённых логов.
     *
     * @return настроенный HTTP-клиент
     */
    @Bean
    public HttpClient logHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }


    /**
     * Предоставляет стандартный поток консольного вывода.
     *
     * @return поток консольного вывода
     */
    @Bean(name = "consoleOutput", destroyMethod = "")
    public PrintStream consoleOutput() {
        return System.out;
    }
}

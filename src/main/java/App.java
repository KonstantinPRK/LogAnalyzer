import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа в консольное приложение анализа NGINX-логов.
 */
@SpringBootApplication(scanBasePackages = "application")
public class App {
    /**
     * Создает корневую конфигурацию приложения.
     */
    public App() {
    }


    /**
     * Запускает контекст Spring Boot и передаёт ему аргументы
     * командной строки.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

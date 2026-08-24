import java.time.OffsetDateTime;

public record NGINXlog(
        String ip,                 // IP-адрес клиента (например, "217.168.17.5")
        OffsetDateTime timestamp,  // Дата и время запроса с учетом таймзоны
        String method,             // HTTP-метод ("GET", "POST" и т.д.)
        String resource,           // Путь к запрашиваемому файлу/ресурсу ("/downloads/product_1")
        int status,                // HTTP-код ответа (404, 200, 304)
        long bodyBytesSent,        // Размер ответа в байтах (337, 332, 0)
        String referer,            // Откуда пришли ("-", если пустой)
        String userAgent           // Браузер/клиент ("Debian APT-HTTP/1.3...")
) {}
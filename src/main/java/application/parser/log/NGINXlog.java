package application.parser.log;

import java.time.OffsetDateTime;

/**
 * Типизированное представление записи NGINX Combined Log.
 *
 * @param remoteAddress адрес клиента
 * @param remoteUser имя удалённого пользователя
 * @param timestamp время запроса
 * @param method HTTP-метод
 * @param resource запрошенный ресурс
 * @param protocol версия HTTP-протокола
 * @param status код ответа
 * @param bodyBytesSent размер тела ответа в байтах
 * @param referer источник перехода
 * @param userAgent пользовательский агент
 */
public record NGINXlog(
        String remoteAddress,
        String remoteUser,
        OffsetDateTime timestamp,
        String method,
        String resource,
        String protocol,
        int status,
        long bodyBytesSent,
        String referer,
        String userAgent
) {
}

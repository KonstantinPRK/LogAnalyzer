package application.components.parsing.request;


import application.userInterface.console.format.Format;

import java.time.LocalDateTime;

public record Request(
        String[] paths,          // путь к логам (локальный шаблон или URL)
        LocalDateTime from,   // начальная дата (опционально)
        LocalDateTime to,     // конечная дата (опционально)
        Format format         // "markdown" или "adoc" (опционально)
) {}

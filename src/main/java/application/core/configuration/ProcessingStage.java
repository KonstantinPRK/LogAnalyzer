package application.core.configuration;

public enum ProcessingStage {
    DECRYPTOR,
    SECURITY_CHECK,
    DATA_PARSING,
    LOG_ANALYSIS,
    REPORT_GENERATION;

    // Если в будущем добавится новый компонент,
    // его просто вставляют в нужное место в этом списке.
}

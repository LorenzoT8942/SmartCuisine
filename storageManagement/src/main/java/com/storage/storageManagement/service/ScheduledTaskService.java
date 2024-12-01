package com.storage.storageManagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    /**
     * Method that will be executed every 1 minute.
     * You can replace the logic inside this method as per your requirements.
     */
    @Scheduled(fixedRate = 60000)  // 60000 ms = 1 minuto
    public void performTaskEveryMinute() {
        // Logica da eseguire ogni minuto
        logger.info("Esecuzione del task schedulato ogni 1 minuto.");

        // TODO: Implementa la logica che deve essere eseguita qui
        // Ad esempio, puoi chiamare un servizio, fare delle operazioni su un database, ecc.
    }
}

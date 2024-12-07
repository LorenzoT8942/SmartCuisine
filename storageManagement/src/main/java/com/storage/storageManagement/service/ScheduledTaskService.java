package com.storage.storageManagement.service;

import com.storage.storageManagement.dtos.request.NotificationRequestDto;

import com.storage.storageManagement.model.StorageList;
import com.storage.storageManagement.repository.StorageListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    @Autowired
    private StorageListRepository storageListRepository;

    @Value("${authentication.service.apikeyNotification}")
    private String apiKeyNotification;

    @Value("${notification.URL}")
    private String notificationURL;


    /**
     * Method that will be executed every 1 minute.
     */
    @Scheduled(fixedRate = 60000)  // 60000 ms = 1 minute
    public void checkExpiredProductsAndNotify() {
        logger.info("Starting scheduled task to check for expired products.");

        // Fetch all products where the expiry date is before today
        LocalDate today = LocalDate.now();
        List<StorageList> expiredProducts = storageListRepository.findByExpiryDateBefore(today);

        if (!expiredProducts.isEmpty()) {
            logger.info("Found {} expired products. Sending notifications.", expiredProducts.size());

            RestTemplate restTemplate = new RestTemplate();

            for (StorageList product : expiredProducts) {
                try {
                    NotificationRequestDto notificationRequest = new NotificationRequestDto(
                            "The product with ID " + product.getId().getIngredientId() + " has expired.",
                            product.getId().getUsername()
                    );

                    // Send notification to the Notification Service


                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    headers.set("Authorization", apiKeyNotification);
                    Map<String, String> requestPayload = new HashMap<>();
                    requestPayload.put("username", notificationRequest.getUsername());
                    requestPayload.put("content", notificationRequest.getContent());

                    // Wrap the request payload in an HttpEntity with headers
                    HttpEntity<Object> requestEntity = new HttpEntity<>(requestPayload, headers);

                    // Make the POST request
                    restTemplate.exchange(notificationURL, HttpMethod.POST, requestEntity, String.class);

                    logger.info("Notification sent for product {} belonging to user {}.",
                            product.getId().getIngredientId(), product.getId().getUsername());
                } catch (Exception e) {
                    logger.error("Error sending notification for product {} belonging to user {}: {}",
                            product.getId().getIngredientId(), product.getId().getUsername(), e.getMessage());
                }
            }
        } else {
            logger.info("No expired products found.");
        }
    }
}

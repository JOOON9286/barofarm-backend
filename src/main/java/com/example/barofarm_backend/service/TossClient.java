package com.example.barofarm_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossClient {

    @Value("${toss.base-url}")
    private String baseUrl;

    @Value("${toss.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**  1. 결제 요청 (ready) */
    public Map<String, Object> requestPayment(String orderId, String orderName, int amount, String successUrl, String failUrl) {
        // Toss 결제 API URL (정식 REST)
        String url = baseUrl + "/payments"; // 올바른 주소

        // Authorization 헤더 생성
        String authHeader = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        // 요청 Body 구성
        Map<String, Object> body = new HashMap<>();
        body.put("method", "CARD");
        body.put("amount", amount);
        body.put("orderId", orderId);
        body.put("orderName", orderName);
        body.put("successUrl", successUrl);
        body.put("failUrl", failUrl);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            System.out.println(" Toss 결제 준비 성공: " + response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println(" Toss API Error: " + ex.getResponseBodyAsString());
            throw ex;
        }
    }

    /**  2. 결제 승인 */
    public Map<String, Object> confirm(String paymentKey, String orderId, Integer amount) {
        String url = baseUrl + "/payments/confirm";

        String authHeader = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody();
    }

    /**  3. 결제 취소 */
    public Map<String, Object> cancelByPaymentKey(String paymentKey, String cancelReason) {
        String url = baseUrl + "/payments/" + paymentKey + "/cancel";

        String authHeader = "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        Map<String, Object> body = new HashMap<>();
        body.put("cancelReason", cancelReason);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody();
    }
}
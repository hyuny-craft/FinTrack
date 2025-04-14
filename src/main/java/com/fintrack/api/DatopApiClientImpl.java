package com.fintrack.api;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DatopApiClientImpl implements DatopApiClient {

    private final WebClient webClient;

    @Value("${kftc.api.base-url}")
    private String baseUrl;

    @Value("${kftc.api.auth-key}")
    private String authKey;

    @Override
    public DatopResponse getTransactionHistory(DatopRequest request) {
        return webClient.post()
                .uri(baseUrl + "/v1/common/transfer")
                .header("Authorization", "Bearer " + authKey)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody ->
                                Mono.error(new RuntimeException("API error: " + errorBody))))
                .bodyToMono(DatopResponse.class)
                .block();
    }
}

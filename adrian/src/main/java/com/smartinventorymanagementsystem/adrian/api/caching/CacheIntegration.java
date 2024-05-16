
package com.smartinventorymanagementsystem.adrian.api.caching;
import com.smartinventorymanagementsystem.adrian.dtos.BaseDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CacheDTO;
import com.smartinventorymanagementsystem.adrian.mappers.CacheDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CacheIntegration {

    //TODO implement api requests to and from microservice.
    //TODO create logic for signaling that resources are available in microservice

    private final WebClient webClient;
    private final CacheDTOMapper cacheDTOMapper;

    @Autowired
    public CacheIntegration(WebClient.Builder webClientBuilder, CacheDTOMapper cacheDTOMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build();
        this.cacheDTOMapper = cacheDTOMapper;
    }

    //Fetch object from cache
    public<T extends BaseDTO>Mono<CacheDTO<T>> getCacheObject(Class<T> type, Long id) {
        String key = Long.toString(id);
        return webClient.get()
                .uri("/request")
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    try {
                        System.out.println("Fetched from cache: " + json);
                        return cacheDTOMapper.fromJson(json, type);
                    } catch (Exception exception) {
                        throw new RuntimeException("Failed to deserialize cache items", exception);
                    }
                });
    }

    //Send new object to cache
    public <T extends BaseDTO> Mono<Void> saveCacheItem(CacheDTO<T> cacheDTO) {
        return webClient.post()
                .uri("/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cacheDTO)
                .retrieve()
                .bodyToMono(Void.class);

    }
}


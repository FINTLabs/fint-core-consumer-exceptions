package no.fintlabs.consumer.exceptions.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import no.fintlabs.status.models.error.ConsumerError
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CacheConfig {

    @Bean
    fun consumerErrorCache(): Cache<String, ConsumerError> =
        Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .build()

}
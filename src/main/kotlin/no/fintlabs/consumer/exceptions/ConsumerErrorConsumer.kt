package no.fintlabs.consumer.exceptions

import com.github.benmanes.caffeine.cache.Cache
import no.fintlabs.kafka.event.EventConsumerConfiguration
import no.fintlabs.kafka.event.EventConsumerFactoryService
import no.fintlabs.kafka.event.topic.EventTopicNameParameters
import no.fintlabs.status.models.error.ConsumerError
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Bean
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.stereotype.Component

@Component
class ConsumerErrorConsumer(
    private val errorCache: Cache<String, ConsumerError>
) {

    @Bean
    fun consumeConsumerError(
        factoryService: EventConsumerFactoryService
    ): ConcurrentMessageListenerContainer<String, ConsumerError> =
        factoryService.createFactory(
            ConsumerError::class.java,
            ::handleConsumerError,
            EventConsumerConfiguration.builder()
                .seekingOffsetResetOnAssignment(true)
                .build()
        ).createContainer(
            EventTopicNameParameters.builder()
                .orgId("fintlabs-no")
                .domainContext("fint-core")
                .eventName("consumer-error")
                .build()
        )

    fun handleConsumerError(consumerRecord: ConsumerRecord<String, ConsumerError>) =
        errorCache.put(consumerRecord.key(), consumerRecord.value())

}
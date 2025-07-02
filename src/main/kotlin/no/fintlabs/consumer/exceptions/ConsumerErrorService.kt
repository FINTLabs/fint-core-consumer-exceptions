package no.fintlabs.consumer.exceptions

import com.github.benmanes.caffeine.cache.Cache
import no.fintlabs.status.models.error.ConsumerError
import org.springframework.stereotype.Service

@Service
class ConsumerErrorService(
    private val cache: Cache<String, ConsumerError>
) {
    fun all(): List<ConsumerError> =
        cache.asMap().values
            .sortedByDescending(ConsumerError::time)

    fun byOrg(org: String): List<ConsumerError> =
        cache.asMap().values
            .filter { it.org == org }
            .sortedByDescending(ConsumerError::time)

    fun one(id: String): ConsumerError? = cache.getIfPresent(id)

    fun stacktrace(id: String): String? = one(id)?.let(ConsumerErrorFormatter::stacktrace)
}

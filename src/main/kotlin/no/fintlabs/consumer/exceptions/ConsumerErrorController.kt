package no.fintlabs.consumer.exceptions

import com.github.benmanes.caffeine.cache.Cache
import no.fintlabs.status.models.error.ConsumerError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/consumer-error")
class ConsumerErrorController(
    private val errorCache: Cache<String, ConsumerError>
) {

    @GetMapping
    fun getConsumerErrors() = errorCache.asMap().values

    @GetMapping("/{id}")
    fun getConsumerError(@PathVariable id: String) =
        errorCache.getIfPresent(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}/stacktrace")
    fun getFormattedStacktrace(@PathVariable id: String): ResponseEntity<String> =
        errorCache.getIfPresent(id)
            ?.let { ResponseEntity.ok(formatException(it)) }
            ?: ResponseEntity.notFound().build()

    private fun formatException(error: ConsumerError): String =
        (listOf(error.name + (error.message?.let { ": $it" } ?: "")) + error.stacktrace.map(::formatFrame))
            .joinToString("\n")

    private fun formatFrame(e: StackTraceElement): String =
        "    at ${e.className}.${e.methodName}(" +
                when {
                    e.isNativeMethod -> "Native Method"
                    e.fileName.isNullOrBlank() || e.lineNumber < 0 -> "Unknown Source"
                    else -> "${e.fileName}:${e.lineNumber}"
                } +
                ")"

}
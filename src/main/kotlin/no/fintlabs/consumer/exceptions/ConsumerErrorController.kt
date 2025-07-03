package no.fintlabs.consumer.exceptions

import no.fintlabs.consumer.exceptions.model.ErrorStats
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/consumer-error")
class ConsumerErrorController(
    private val service: ConsumerErrorService
) {

    @GetMapping
    fun getConsumerErrors() = service.all()

    @GetMapping("/stats")
    fun getStats() =
        service.all()
            .toOrgPkgStats()
            .mapValues { (_, pkgStats) -> pkgStats.toResponse() }

    @GetMapping("/{id}")
    fun getConsumerError(@PathVariable id: String) =
        service.one(id)
            ?.let{ ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/org/{org}")
    fun getConsumerErrorsByOrg(@PathVariable org: String) = service.byOrg(org)

    @GetMapping("/{id}/stacktrace")
    fun getFormattedStacktrace(@PathVariable id: String) =
        service.stacktrace(id)
            ?.let{ ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    private fun Map<String, ErrorStats>.toResponse() =
        mapOf("totalErrorCount" to values.sumOf(ErrorStats::errorCount)) + this

}

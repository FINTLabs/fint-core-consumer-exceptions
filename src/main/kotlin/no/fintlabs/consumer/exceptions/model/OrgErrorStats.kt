package no.fintlabs.consumer.exceptions.model

data class OrgErrorStats(
    val errorCount: Int,
    val errors: Map<String, Int>
)

package no.fintlabs.consumer.exceptions.model

data class ErrorStats(
    val errorCount: Int,
    val errors: Map<String, Int>
)

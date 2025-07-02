package no.fintlabs.consumer.exceptions

import no.fintlabs.status.models.error.ConsumerError

object ConsumerErrorFormatter {
    fun stacktrace(error: ConsumerError): String =
        (sequenceOf(error.name + (error.message?.let { ": $it" } ?: "")) +
                error.stacktrace.asSequence().map(::frame))
            .joinToString("\n")

    private fun frame(e: StackTraceElement): String =
        "    at ${e.className}.${e.methodName}(" +
                when {
                    e.isNativeMethod -> "Native Method"
                    e.fileName.isNullOrBlank() || e.lineNumber < 0 -> "Unknown Source"
                    else -> "${e.fileName}:${e.lineNumber}"
                } +
                ")"
}

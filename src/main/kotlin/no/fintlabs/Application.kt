package no.fintlabs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FintCoreConsumerExceptionsApplication

fun main(args: Array<String>) {
    runApplication<FintCoreConsumerExceptionsApplication>(*args)
}

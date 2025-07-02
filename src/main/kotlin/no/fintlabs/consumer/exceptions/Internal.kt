package no.fintlabs.consumer.exceptions

import no.fintlabs.consumer.exceptions.model.ErrorStats
import no.fintlabs.status.models.error.ConsumerError

internal fun Collection<ConsumerError>.toOrgPkgStats(): Map<String, Map<String, ErrorStats>> =
    groupBy(ConsumerError::org).mapValues { (_, orgErrors) ->
        orgErrors.groupBy { "${it.domain}-${it.pkg}" }.mapValues { (_, dpErrors) ->
            ErrorStats(
                dpErrors.size,
                dpErrors.groupingBy(ConsumerError::name).eachCount()
            )
        }
    }

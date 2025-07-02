package no.fintlabs.consumer.exceptions

import no.fintlabs.consumer.exceptions.model.OrgErrorStats
import no.fintlabs.status.models.error.ConsumerError

internal fun Collection<ConsumerError>.toOrgStats(): Map<String, OrgErrorStats> =
    groupBy { it.org }.mapValues { (_, orgErrors) ->
        OrgErrorStats(
            orgErrors.size,
            orgErrors.groupingBy { it.name }.eachCount()
        )
    }
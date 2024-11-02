package org.mcse.data.transform.models.logs

import java.math.BigDecimal
import java.time.LocalDateTime

data class LogEntry(
    val date: LocalDateTime,
    val time: BigDecimal,
    val connection: ConnectionLogItem,
    val upstream: UpstreamLogItem,
    val response: ResponseLogItem,
    val request: RequestLogItem,
)

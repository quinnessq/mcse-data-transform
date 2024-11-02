package org.mcse.data.transform.service

import org.mcse.data.transform.models.doa.Connection
import org.mcse.data.transform.models.doa.Entry
import org.mcse.data.transform.models.doa.Request
import org.mcse.data.transform.models.doa.Response
import org.mcse.data.transform.models.doa.Upstream
import org.mcse.data.transform.models.logs.LogEntry
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LogEntryToDoaMapper {

    fun mapLogEntryToDao(logEntry: LogEntry): Entry {

        val entry = Entry(
            date = logEntry.date,
            time = logEntry.time,
            malicious = false,
        )

        val connection = Connection(
            remoteIp = logEntry.connection.remoteIp,
            remotePort = logEntry.connection.remotePort,
            connectionId = logEntry.connection.connection,
            connectionTime = logEntry.connection.connectionTime,
        )

        val upstream = Upstream(
            upstreamResponseTime = logEntry.upstream.upstreamResponseTime,
            upstreamResponseLength = logEntry.upstream.upstreamResponseLength,
            upstreamStatus = extractStatus(logEntry.upstream.upstreamStatus),
            upstreamConnectionTime = extractTime(logEntry.upstream.upstreamConnectionTime),
        )

        val response = Response(
            responseBodySize = logEntry.response.responseBodySize,
            responseTotalSize = logEntry.response.responseTotalSize,
            responseStatus = logEntry.response.responseStatus,
            responseTime = logEntry.response.responseTime,
        )

        val request = Request(
            authenticated = extractAuthenicated(logEntry.request.authenticated),
            requestLength = logEntry.request.requestLength,
            requestContentLength = logEntry.request.requestContentLength,
            requestContentType = logEntry.request.requestContentType,
            requestMethod = logEntry.request.requestMethod,
            requestUri = logEntry.request.requestUri,
            referrer = logEntry.request.referrer,
            protocol = logEntry.request.protocol,
            userAgent = logEntry.request.userAgent,
        )

        return entry.copy(
            connection = connection,
            upstream = upstream,
            response = response,
            request = request,
        )
    }

    private fun extractTime(upstreamConnectionTime: String?): BigDecimal? {
        if(upstreamConnectionTime != null) {
            return try {
                upstreamConnectionTime.toBigDecimal()
            } catch(ex: Exception) {
                BigDecimal.ZERO
            }
        }

        return null
    }

    private fun extractAuthenicated(authenticated: String): Boolean {
        return when(authenticated) {
            "true", "1" -> true
            else -> false
        }
    }

    private fun extractStatus(status: String): Int {
        return try {
            status.toInt()
        } catch(e: Exception) {
            200
        }
    }

}

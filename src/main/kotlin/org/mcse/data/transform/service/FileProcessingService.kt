package org.mcse.data.transform.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.mcse.data.transform.extensions.log
import org.mcse.data.transform.models.logs.LogEntry
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileProcessingService(
    private val objectMapper: ObjectMapper,
    private val logEntryToDoaMapper: LogEntryToDoaMapper,
    private val entryDomainService: EntryDomainService,
) {

    @Async
    fun processFile(file: File): Int {
        return readFilePerLine(file)
    }

    private fun readFilePerLine(file: File): Int {
        var failureCount = 0
        file.forEachLine { line ->
            //filter out all static assets (images, pdf) they are served from disk directly and are not part of the REST API
            //filter out some ingress controller logging that is not in the desired format.
            if(line.contains("/assets/").not() && line.contains("7 controller.go:").not()) {
                if(convertLine(line).not()) {
                    failureCount++
                }
            }
        }
        return failureCount
    }

    private fun convertLine(line: String): Boolean {
        return try {
            val logEntry = objectMapper.readValue(line, LogEntry::class.java)
            //filter out some other assets from the data
            if(shouldFilterLogEntry(logEntry).not()) {
                val mapped = logEntryToDoaMapper.mapLogEntryToDao(logEntry)
                entryDomainService.saveEntity(mapped)
            }
            true
        } catch(ex: Exception) {
            log.error("Error parsing log entry: $line with error: ${ex.message}")
            false
        }
    }

    private fun shouldFilterLogEntry(logEntry: LogEntry): Boolean {
        return when {
            logEntry.request.requestUri.endsWith(".js") -> true
            logEntry.request.requestUri.endsWith(".ico") -> true
            logEntry.request.requestUri.endsWith(".woff2") -> true
            logEntry.request.requestUri.endsWith(".ttf") -> true
            else -> false
        }
    }
}

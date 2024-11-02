package org.mcse.data.transform.service

import jakarta.annotation.PostConstruct
import org.apache.commons.io.FileUtils
import org.mcse.data.transform.extensions.log
import org.springframework.stereotype.Service
import java.io.File

@Service
class DataTransformationService(
    private val fileProcessingService: FileProcessingService
) {

    companion object {
        private const val RESOURCE_FOLDER = "C:\\Users\\alcui\\Desktop\\MSCE\\Modules\\Afstuderen\\ekomenu-log"
    }

    private val fileList: MutableSet<File> = mutableSetOf()
    private var failureCount: Long = 0


    @PostConstruct
    fun init() {
        createFileList()
        loadFiles()
    }

    private fun loadFiles() {
        fileList.forEach { file: File ->
            failureCount += fileProcessingService.processFile(file)
        }
        log.info("Failure count: $failureCount")
    }

    fun createFileList() {
        val files = FileUtils.listFiles(File(RESOURCE_FOLDER), null, true)
        fileList.addAll(files)
    }

}

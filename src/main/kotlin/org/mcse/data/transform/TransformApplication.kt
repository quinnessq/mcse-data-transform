package org.mcse.data.transform

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@SpringBootApplication
class TransformApplication {

	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<TransformApplication>(*args)
		}
	}

	@Bean(name = ["asyncExecutor"])
	fun getAsyncExecutor(): Executor {
		val executor = ThreadPoolTaskExecutor()
		executor.corePoolSize = 96 // Set the core pool size
		executor.maxPoolSize = 96 // Set the maximum pool size
		executor.queueCapacity = 100 // Set the capacity of the task queue
		executor.setThreadNamePrefix("custom-async-") // Set the thread name prefix
		executor.initialize()
		return executor
	}

	@Bean
	@Primary
	fun objectMapper(): ObjectMapper = jacksonObjectMapper()
		.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
		.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		.setSerializationInclusion(JsonInclude.Include.NON_NULL)
		.registerModule(JavaTimeModule())
}

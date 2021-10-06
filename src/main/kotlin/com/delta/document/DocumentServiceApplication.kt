package com.delta.document


import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(
	title = "Horizon API",
	version = "3.0.0",
	description = "Information regarding Horizon Client"
)
)
open class DocumentServiceApplication

fun main(args: Array<String>) {
	runApplication<DocumentServiceApplication>(*args)
}

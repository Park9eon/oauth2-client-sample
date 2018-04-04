package com.park9eon.home

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application

fun main(vararg args: String) {
    run(Application::class.java, *args)
}

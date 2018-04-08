package com.park9eon.home

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan
@SpringBootApplication
open class Application

fun main(vararg args: String) {
    System.setProperty("spring.devtools.restart.enabled", "false")
    run(Application::class.java, *args)
}

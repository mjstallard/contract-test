package io.pivotal.iad.contracttests

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ContractTestsApplication {
    private val log = LoggerFactory.getLogger(ContractTestsApplication::class.java)

    @Bean
    fun clr(foosRepository: FoosRepository): CommandLineRunner {
        return CommandLineRunner {
            log.info("Making bar")
            foosRepository.create(Foo(name = "bar"))

            val foos = foosRepository.findAll()
            log.info("foos: " + foos)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ContractTestsApplication::class.java)
        }
    }

}


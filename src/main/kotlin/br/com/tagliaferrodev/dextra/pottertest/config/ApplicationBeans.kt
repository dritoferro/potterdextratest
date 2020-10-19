package br.com.tagliaferrodev.dextra.pottertest.config

import com.github.kittinunf.fuel.core.FuelManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationBeans {

    @Bean
    fun client(): FuelManager {
        val client = FuelManager.instance

        client.timeoutInMillisecond = 5000000
        client.timeoutReadInMillisecond = 5000000

        return client
    }
}
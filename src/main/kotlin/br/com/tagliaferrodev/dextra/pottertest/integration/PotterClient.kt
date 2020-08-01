package br.com.tagliaferrodev.dextra.pottertest.integration

import br.com.tagliaferrodev.dextra.pottertest.entity.House
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class PotterClient {

    @Value("\${integration.base-url}")
    private val baseUrl: String = ""

    @Value("\${integration.path-houses}")
    private val housesPath: String = ""

    @Value("\${integration.key}")
    private val key: String = ""

    @Async
    fun getData() {
        val client = buildClient()

        val url = "$housesPath?key=$key"

        val housesFlux = client.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(House::class.java)

        housesFlux.subscribe {
            println(it)
            println("----------------")
        }

    }

    private fun buildClient(): WebClient {
        return WebClient.create(baseUrl)
    }
}
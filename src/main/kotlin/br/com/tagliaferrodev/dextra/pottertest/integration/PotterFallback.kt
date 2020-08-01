package br.com.tagliaferrodev.dextra.pottertest.integration

import br.com.tagliaferrodev.dextra.pottertest.house.House
import org.slf4j.LoggerFactory

class PotterFallback(private val cause: Throwable?) : PotterExecutor {

    private val logger = LoggerFactory.getLogger(PotterFallback::class.java)

    override fun getHouses(path: String, query: HashMap<String, String>): List<House> {
        logger.error("Entrou no Fallback de getHouses", cause?.message)
        return emptyList()
    }

    override fun getHouseById(path: String, id: String, query: HashMap<String, String>): List<House> {
        logger.error("Entrou no Fallback de getHouseById", cause?.message)
        return emptyList()
    }
}
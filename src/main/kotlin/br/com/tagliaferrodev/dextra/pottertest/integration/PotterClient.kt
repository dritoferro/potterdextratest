package br.com.tagliaferrodev.dextra.pottertest.integration

import br.com.tagliaferrodev.dextra.pottertest.house.House
import br.com.tagliaferrodev.dextra.pottertest.util.RedisUtils
import feign.Retryer
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.persistence.EntityNotFoundException

@Service
class PotterClient(private val redisUtils: RedisUtils) {

    @Value("\${integration.base-url}")
    private val baseUrl: String = ""

    @Value("\${integration.path-houses}")
    private val housesPath: String = ""

    @Value("\${integration.key}")
    private val key: String = ""

    private val logger = LoggerFactory.getLogger(PotterClient::class.java)

    private val keyMap by lazy {
        hashMapOf("key" to key)
    }

    fun findByHouseId(id: String): House {
        try {
            val cachedHouse = redisUtils.get<House>(id)

            if (cachedHouse != null && !shouldUpdate(cachedHouse)) {
                return cachedHouse
            }
            val client = buildClient()

            val refreshedHouse = client.getHouseById(housesPath, id, keyMap)

            if (!refreshedHouse.isNullOrEmpty()) {
                update(refreshedHouse)

                return refreshedHouse[0]
            } else {
                throw EntityNotFoundException("This house $id does not exist.")
            }

        } catch (ex: Exception) {
            logger.error(ex.message)
            throw ex
        }
    }

    fun updateCache(): Boolean {
        return try {
            val client = buildClient()

            val houses = client.getHouses(housesPath, keyMap)

            if (houses.isNullOrEmpty()) {
                return false
            }

            update(houses)

            true
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw ex
        }
    }

    private fun shouldUpdate(house: House): Boolean {
        val now = LocalDateTime.now()
        return house.updatedAt?.isBefore(now.minusDays(1))!!
    }

    private fun update(houses: List<House>) {
        houses.forEach { house ->
            house.updatedAt = LocalDateTime.now()
            redisUtils.save(house._id!!, house)
        }
    }

    private fun buildClient(): PotterExecutor {
        return HystrixFeign.builder()
                .client(OkHttpClient())
                .encoder(JacksonEncoder())
                .decoder(JacksonDecoder())
                .retryer(Retryer.Default(500, TimeUnit.SECONDS.toMillis(2), 3))
                .target<PotterExecutor>(PotterExecutor::class.java, baseUrl) { PotterFallback(it) }
    }
}

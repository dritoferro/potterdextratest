package br.com.tagliaferrodev.dextra.pottertest.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class RedisUtils(val om: ObjectMapper) {

    @Resource(name = "redisTemplate")
    lateinit var redis: ValueOperations<String, String>

    val logger = LoggerFactory.getLogger(RedisUtils::class.java)

    fun save(key: String, value: Any): Boolean {
        try {
            val data = om.writeValueAsString(value)

            redis.set(key, data)

            return true
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw ex
        }
    }

    final inline fun <reified T> get(key: String): T? {
        return try {
            val search = redis.get(key)

            if (!search.isNullOrBlank()) {
                om.readValue<T>(search, T::class.java)
            } else {
                null
            }
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw ex
        }
    }
}

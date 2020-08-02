package br.com.tagliaferrodev.dextra.pottertest.house

import java.time.LocalDateTime

data class House(
        val _id: String? = null,
        val name: String? = null,
        val mascot: String? = null,
        val headOfHouse: String? = null,
        val houseGhost: String? = null,
        val founder: String? = null,
        val values: List<String>? = emptyList(),
        val colors: List<String>? = emptyList()
) {
    var updatedAt: LocalDateTime? = null
}

package br.com.tagliaferrodev.dextra.pottertest.house

import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("houses")
class HouseController(val potterClient: PotterClient) {

    @PutMapping("update-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateAllHouses(principal: Principal): ResponseEntity<Any> {
        val updated = potterClient.updateCache()

        val toReturn = object {
            val success = updated
        }

        return if (updated) {
            ResponseEntity.ok(toReturn)
        } else {
            ResponseEntity(toReturn, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("{id}")
    fun getHouseById(@PathVariable id: String): ResponseEntity<House> {
        return ResponseEntity.ok(potterClient.findByHouseId(id))
    }
}
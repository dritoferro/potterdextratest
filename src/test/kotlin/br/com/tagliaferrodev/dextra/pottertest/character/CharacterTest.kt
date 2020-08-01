package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import javax.persistence.EntityNotFoundException

@ExtendWith(MockitoExtension::class)
class CharacterTest {

    private val character = Character(
            name = "Test",
            house = "5a05e2b252f721a3cf2ea33f",
            school = "Hogwarts School of Witchcraft and Wizardry",
            patronus = "owl"
    )

    @Mock
    lateinit var repository: CharacterRepository

    @Mock
    lateinit var partner: PotterClient

    @InjectMocks
    lateinit var service: CharacterService

    @Test
    fun saveEntitySuccessfully() {
        `when`(repository.save(character)).thenReturn(character.copy(id = UUID.randomUUID()))

        val persist = service.save(character)

        assertNotNull(persist.id)

        verify(repository, times(1)).save(character)
    }

    @Test
    fun saveEntityWithUnknownHouseThrowError() {
        `when`(partner.findByHouseId("")).thenThrow(EntityNotFoundException::class.java)

        assertThrows<EntityNotFoundException> {
            service.save(character.copy(house = ""))
        }
    }
}
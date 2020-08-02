package br.com.tagliaferrodev.dextra.pottertest.character

import br.com.tagliaferrodev.dextra.pottertest.character.domain.Character
import br.com.tagliaferrodev.dextra.pottertest.house.House
import br.com.tagliaferrodev.dextra.pottertest.integration.PotterClient
import org.junit.jupiter.api.Assertions.assertEquals
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

    private val characters = listOf(
            Character(
                    name = "João",
                    house = "5a05e2b252f721a3cf2ea33f",
                    school = "Hogwarts School of Witchcraft and Wizardry",
                    patronus = "owl"
            ),
            Character(
                    name = "Test 2",
                    house = "5a05e2b252f721a3cf2ea33f",
                    school = "Hogwarts School of Witchcraft and Wizardry"
            ),
            Character(
                    name = "Joana",
                    house = "5a05dc8cd45bd0a11bd5e071",
                    school = "Hogwarts School of Witchcraft and Wizardry",
                    patronus = "cow"
            ),
            Character(
                    name = "Joca",
                    house = "5a05e2b252f721a3cf2ea33f",
                    school = "Hogwarts School of Witchcraft and Wizardry"
            ),
            Character(
                    name = "Test 5",
                    house = "5a05dc8cd45bd0a11bd5e071",
                    school = "Hogwarts School of Witchcraft and Wizardry"
            )
    )

    private val gryffindor = House(
            _id = "5a05e2b252f721a3cf2ea33f",
            name = "Gryffindor",
            mascot = "lion",
            headOfHouse = "Minerva McGonagall",
            houseGhost = "Nearly Headless Nick",
            founder = "Goderic Gryffindor"
    )

    private val slytherin = House(
            _id = "5a05dc8cd45bd0a11bd5e071",
            name = "Slytherin",
            mascot = "serpent",
            headOfHouse = "Severus Snape",
            houseGhost = "The Bloody Baron",
            founder = "Salazar Slytherin"
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

    @Test
    fun findByCharacterId() {
        val id = UUID.randomUUID()
        `when`(repository.findById(id)).thenReturn(Optional.of(character.copy(id = id)))
        `when`(partner.findByHouseId(gryffindor._id!!)).thenReturn(gryffindor)

        val result = service.findById(id.toString())

        assertEquals(character.name, result.character.name)
        assertEquals(gryffindor.headOfHouse, result.house.headOfHouse)
    }

    @Test
    fun findWithoutParams() {
        `when`(repository.findAll()).thenReturn(characters)
        `when`(partner.findByHouseId(gryffindor._id!!)).thenReturn(gryffindor)
        `when`(partner.findByHouseId(slytherin._id!!)).thenReturn(slytherin)

        val result = service.findByAttributes(null, null)

        val charactersFromGryffindor = result.find { it.house == gryffindor }?.characters
        val charactersFromSlytherin = result.find { it.house == slytherin }?.characters

        assertEquals(3, charactersFromGryffindor?.size)
        assertEquals(2, charactersFromSlytherin?.size)
    }

    @Test
    fun findByHouse() {
        `when`(repository.findAllByHouse(gryffindor._id!!))
                .thenReturn(Optional.of(characters.filter { it.house == gryffindor._id }))
        `when`(partner.findByHouseId(gryffindor._id!!)).thenReturn(gryffindor)

        val result = service.findByAttributes(null, gryffindor._id)

        assertEquals(3, result[0].characters.size)
        assertEquals(gryffindor, result[0].house)
    }

    @Test
    fun findByName() {
        `when`(repository.findAllByNameContaining("Jo"))
                .thenReturn(Optional.of(characters.filter { it.name?.contains("Jo")!! }))
        `when`(partner.findByHouseId(gryffindor._id!!)).thenReturn(gryffindor)
        `when`(partner.findByHouseId(slytherin._id!!)).thenReturn(slytherin)

        val result = service.findByAttributes("Jo", null)

        val charactersFromGryffindor = result.find { it.house == gryffindor }?.characters
        val charactersFromSlytherin = result.find { it.house == slytherin }?.characters

        assertEquals(2, charactersFromGryffindor?.size)
        assertEquals(1, charactersFromSlytherin?.size)
    }
}
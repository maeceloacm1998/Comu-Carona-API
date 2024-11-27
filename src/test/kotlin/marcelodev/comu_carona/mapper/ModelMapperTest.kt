package marcelodev.comu_carona.mapper

import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.Example
import marcelodev.comu_carona.v1.CarRideVO
import marcelodev.comu_carona.v1.ExampleVO
import org.junit.jupiter.api.Assertions.assertEquals
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import kotlin.test.Test


class ModelMapperTest {

    private val modelMapper = ModelMapper()

    @Test
    fun testCarRideToCarRideVOMapping() {
        val carRide = CarRide(
            id = 1,
            userId = "user123",
            createdAt = LocalDateTime.now(),
            carModel = "Model S",
            carColor = "Red",
            carPlate = "ABC-1234",
            quantitySeats = 4,
            address = "123 Main St",
            hour = "10:00 AM",
            status = "IN_PROGRESS",
            isTwoPassengersBehind = true
        )

        val carRideVO = modelMapper.map(carRide, CarRideVO::class.java)

        assertEquals(carRide.carModel, carRideVO.carModel)
        assertEquals(carRide.carColor, carRideVO.carColor)
        assertEquals(carRide.carPlate, carRideVO.carPlate)
        assertEquals(carRide.quantitySeats, carRideVO.quantitySeats)
        assertEquals(carRide.address, carRideVO.address)
        assertEquals(carRide.hour, carRideVO.hour)
        assertEquals(carRide.status, carRideVO.status)
        assertEquals(carRide.isTwoPassengersBehind, carRideVO.isTwoPassengersBehind)
    }

    @Test
    fun testExampleToExampleVOMapping() {
        val example = Example(
            id = 1,
            name = "John Doe",
        )

        val exampleVO = modelMapper.map(example, ExampleVO::class.java)

        assertEquals(example.id, exampleVO.id)
        assertEquals(example.name, exampleVO.name)
    }
}
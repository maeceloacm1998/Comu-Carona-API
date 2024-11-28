package marcelodev.comu_carona.mapper

import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.Example
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.v1.rider.CarRideVO
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
            riderId = "user123",
            createdAt = LocalDateTime.now(),
            carModel = "Model S",
            carColor = "Red",
            carPlate = "ABC-1234",
            quantitySeats = 4,
            waitingAddress = "Rua A",
            destinationAddress = "Rua B",
            waitingHour = "10:00 AM",
            destinationHour = "10:00 AM",
            status = "IN_PROGRESS",
            user = User(),
            isTwoPassengersBehind = true
        )

        val carRideVO = modelMapper.map(carRide, CarRideVO::class.java)

        assertEquals(carRide.carModel, carRideVO.carModel)
        assertEquals(carRide.carColor, carRideVO.carColor)
        assertEquals(carRide.carPlate, carRideVO.carPlate)
        assertEquals(carRide.quantitySeats, carRideVO.quantitySeats)
        assertEquals(carRide.waitingAddress, carRideVO.waitingAddress)
        assertEquals(carRide.destinationAddress, carRideVO.destinationAddress)
        assertEquals(carRide.waitingHour, carRideVO.waitingHour)
        assertEquals(carRide.destinationHour, carRideVO.destinationHour)
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
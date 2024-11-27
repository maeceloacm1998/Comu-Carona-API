package marcelodev.comu_carona.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.services.CarRideService
import marcelodev.comu_carona.v1.CarRideVO
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/car-ride/v1")
@Tag(name = "Car-Rider", description = "End-points to car ride entity")
class CarRideController(
    private val carRideService: CarRideService
) {

    @Operation(
        summary = "Create a car ride",
        description = "This function creates a car ride",
        tags = ["Car Ride"]
    )
    @PostMapping("/create", produces = ["application/json"])
    fun createCarRide(@RequestBody data: CarRideVO): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication

        carRideService.createCarRide(
            carRide = data,
            userId = authentication.name
        )
        return ResponseEntity.status(201).body("Car ride created successfully")
    }
}
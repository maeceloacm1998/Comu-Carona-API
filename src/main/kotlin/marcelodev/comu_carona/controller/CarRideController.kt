package marcelodev.comu_carona.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.services.CarRideService
import marcelodev.comu_carona.v1.CarRideVO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
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
        tags = ["Car Ride"],
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Car ride created successfully"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data"
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized"
            ),
        ]
    )
    @PostMapping("/create", produces = ["application/json"])
    fun createCarRide(@RequestBody data: CarRideVO): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        return when {
            data.carColor.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Username is required")

            data.carModel.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("CarModel is required")

            data.carPlate.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("CarPlate is required")

            data.destinationAddress.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("DestinationAddress is required")

            data.waitingAddress.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("WaitingAddress is required")

            data.hour.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Hour is required")

            data.quantitySeats == null -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("QuantitySeats is required")

            else -> {
                carRideService.createCarRide(
                    carRide = data,
                    userId = authentication.getUserId()
                )
                return ResponseEntity.ok("Car ride created successfully")
            }
        }
    }

    @Operation(
        summary = "Find last car ride",
        description = "This function finds the last car ride",
        tags = ["Car Ride"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride found successfully"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data"
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized"
            )
        ]
    )
    @GetMapping("/find-last", produces = ["application/json"])
    fun findLastCarRide(): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        val lastCarRide = carRideService.findLastCarRideByUserId(authentication.getUserId())
        return ResponseEntity.ok(lastCarRide)
    }
}
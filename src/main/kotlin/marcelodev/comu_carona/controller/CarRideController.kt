package marcelodev.comu_carona.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.services.CarRideService
import marcelodev.comu_carona.v1.rider.AvailableCarRidesVO
import marcelodev.comu_carona.v1.rider.CarRideVO
import marcelodev.comu_carona.v1.rider.DetailsCarRideVO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/car-ride/v1")
@Tag(name = "Car-Rider", description = "End-points to car ride entity")
class CarRideController(
    private val carRideService: CarRideService
) {

    @Operation(
        summary = "Create a car ride",
        description = "This function creates a car ride",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride created successfully",
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

            data.waitingHour.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("waitingHour is required")

            data.destinationHour.isNullOrBlank() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("destinationHour is required")

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
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride found successfully",
                content = [
                    Content(
                        schema = Schema(implementation = CarRideVO::class)
                    )
                ]
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
    private fun findLastCarRide(): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        val lastCarRide = carRideService.findLastCarRideByUserId(authentication.getUserId())
        return ResponseEntity.ok(lastCarRide)
    }


    @Operation(
        summary = "Get available car rides",
        description = "This function retrieves a list of available car rides",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Available car rides retrieved successfully",
                content = [
                    Content(
                        schema = Schema(implementation = AvailableCarRidesVO::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized"
            )
        ]
    )
    @GetMapping("/available", produces = ["application/json"])
    fun getAvailableCarRides(): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        val availableCarRides = carRideService.availableCarRides(authentication.getUserId())
        return ResponseEntity.ok(availableCarRides)
    }

    @Operation(
        summary = "Get car ride details",
        description = "This function retrieves the details of a car ride",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride details retrieved successfully",
                content = [
                    Content(
                        schema = Schema(implementation = DetailsCarRideVO::class)
                    )
                ]
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
    @GetMapping("/details/{id}", produces = ["application/json"])
    fun getCarRideDetails(@PathVariable id: String?): ResponseEntity<*> {
        if (id.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Id is required")
        }

        val lastCarRide = carRideService.findCarRideById(id)
        return ResponseEntity.ok(lastCarRide)
    }

    @Operation(
        summary = "Reserve a car ride",
        description = "This function reserves a car ride for the user",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride reserved successfully"
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
    @PostMapping("/details/reservationRide/{id}", produces = ["application/json"])
    fun reserveCarRide(@PathVariable id: String?): ResponseEntity<*> {
        if (id.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Id is required")
        }

        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User
        carRideService.reserveCarRide(
            carRideId = id,
            user = authentication
        )

        return ResponseEntity.ok("Car ride reserved successfully")
    }

    @Operation(
        summary = "Delete a car ride",
        description = "This function deletes a car ride",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Car ride deleted successfully"
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
    @DeleteMapping("/delete/{id}", produces = ["application/json"])
    fun deleteCarRide(@PathVariable id: String?): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        if (id.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Id is required")
        }

        carRideService.deleteCarRide(
            carRideId = id,
            userId = authentication.getUserId()
        )

        return ResponseEntity.ok("Car ride deleted successfully")
    }
}
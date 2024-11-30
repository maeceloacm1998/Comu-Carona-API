package marcelodev.comu_carona.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.services.CarRideReservationService
import marcelodev.comu_carona.v1.rider.CarRideMyReservationVO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/car-ride-reservations/v1")
@Tag(name = "Car Ride Reservations", description = "End-points to car ride reservations entity")
class CarRideReservationsController(
    private val carRideReservationService: CarRideReservationService,
) {

    @Operation(
        summary = "Find my reservations",
        description = "This function finds all reservations of the user and car ride status too",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Reservations found successfully",
                content = [
                    Content(
                        schema = Schema(
                            implementation = CarRideMyReservationVO::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data, error when trying to find reservations",
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
        ]
    )
    @GetMapping("/my-reservations", produces = ["application/json"])
    fun findMyReservations(
        @Parameter(description = "Status of the reservation to filter by", required = false)
        @RequestParam(required = false) status: String?
    ): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        val myReservation: List<CarRideMyReservationVO> =
            carRideReservationService.findMyReservations(
                userId = authentication.getUserId(),
                status = status
            )
        return ResponseEntity.ok(myReservation)
    }

    @Operation(
        summary = "Create a reservation car ride",
        description = "This function creates a reservation car ride",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Reservation car ride created successfully",
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data",
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
            ),
        ]
    )
    @DeleteMapping("/delete-reservation/{id}", produces = ["application/json"])
    fun deleteReservation(@PathVariable id: String?): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        if (id.isNullOrBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Id is required")
        }

        carRideReservationService.deleteReservationCarRideSpecifUserId(
            carRiderUUID = id,
            userId = authentication.getUserId()
        )
        return ResponseEntity.ok("Reservation deleted successfully")
    }
}
package marcelodev.comu_carona.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import marcelodev.comu_carona.services.ExampleService
import marcelodev.comu_carona.v1.ExampleVO
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/example/v1")
@Tag(name = "Example Endpoint", description = "End-points to example entity")
class ExampleController(
    private val exampleService: ExampleService
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Find all examples",
        description = "Find all examples recorded in the database",
        tags = ["Example"],
        responses = [
            ApiResponse(
                responseCode = "200", description = "Success", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = ExampleVO::class)))
                ]
            ),
            ApiResponse(responseCode = "500", description = "Internal Server Error")
        ]
    )
    fun example(): List<ExampleVO> {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name
        println("User $username is requesting examples")
        return exampleService.findAll()
    }

    @PostMapping
    fun examplePost(@RequestBody example: ExampleVO): ExampleVO {
        return exampleService.save(example)
    }

}
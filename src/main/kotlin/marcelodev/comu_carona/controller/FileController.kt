package marcelodev.comu_carona.controller

import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.services.AuthService
import marcelodev.comu_carona.services.FirebaseService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/files/v1")
class FileController(
    private val firebaseService: FirebaseService,
    private val authService: AuthService
) {

    @PostMapping("/upload/user-image")
    suspend fun uploadFile(@RequestParam("file") file: MultipartFile?): ResponseEntity<*> {
        val authentication: User = SecurityContextHolder.getContext().authentication.principal as User

        if (file == null) {
            return ResponseEntity.badRequest().body("File is required.")
        }

        val downloadUrl: String = firebaseService.uploadFileAndSaveLink(file)
            ?: return ResponseEntity.badRequest().body("Error when trying to upload file.")

        val user = authService.updatePhotoImageUrl(authentication.getUserId(), downloadUrl)

        return ResponseEntity.ok(user)
    }
}
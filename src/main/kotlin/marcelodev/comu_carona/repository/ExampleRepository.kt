package marcelodev.comu_carona.repository

import org.springframework.data.jpa.repository.JpaRepository
import marcelodev.comu_carona.models.Example

interface ExampleRepository: JpaRepository<Example, Long?>
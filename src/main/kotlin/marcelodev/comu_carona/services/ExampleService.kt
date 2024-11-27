package marcelodev.comu_carona.services

import marcelodev.comu_carona.v1.ExampleVO
import org.springframework.stereotype.Service
import marcelodev.comu_carona.exceptions.ResourceNotFoundException
import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.Example
import marcelodev.comu_carona.repository.ExampleRepository
import java.util.logging.Logger

@Service
class ExampleService(
    private val exampleRepository: ExampleRepository,
    private val customMapper: CustomMapper
) {
    private val logger = Logger.getLogger(ExampleService::class.java.name)

    fun findAll(): List<ExampleVO> {
        logger.info("Finding all Examples")
        val examples = exampleRepository.findAll()
        return customMapper.parseListObject(examples, ExampleVO::class.java)
    }

    fun findById(id: Long): ExampleVO {
        logger.info("Finding Example by id: $id")
        val example = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        return customMapper.parseObject(example, ExampleVO::class.java)
    }

    fun save(example: ExampleVO): ExampleVO {
        logger.info("Saving Example with name: ${example.name}")

        val entity = customMapper.parseObject(example, Example::class.java)
        val savedEntity = exampleRepository.save(entity)

        return customMapper.parseObject(savedEntity, ExampleVO::class.java)
    }

    fun update(id: Long, example: ExampleVO): ExampleVO {
        logger.info("Updating Example by id: $id")

        val entity = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        entity.name = example.name
        val updatedEntity = exampleRepository.save(entity)

        return customMapper.parseObject(updatedEntity, ExampleVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting Example by id: $id")
        val entity = exampleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID") }

        exampleRepository.delete(entity)

        logger.info("Example deleted successfully")
    }
}
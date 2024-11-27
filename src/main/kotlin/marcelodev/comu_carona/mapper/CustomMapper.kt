package marcelodev.comu_carona.mapper

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CustomMapper {

    @Autowired
    private lateinit var mapper: ModelMapper

    fun <O, D> parseObject(origin: O, destination: Class<D>): D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parseListObject(origin: List<O>, destination: Class<D>): List<D> {
        val destinationObjects: MutableList<D> = mutableListOf()
        origin.forEach { o -> destinationObjects.add(mapper.map(o, destination)) }
        return destinationObjects
    }
}
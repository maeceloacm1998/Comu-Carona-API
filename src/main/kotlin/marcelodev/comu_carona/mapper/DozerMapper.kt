package marcelodev.comu_carona.mapper

import org.dozer.DozerBeanMapper
import org.dozer.Mapper

object DozerMapper {

    private val mapper: Mapper = DozerBeanMapper()

    fun <O,D> parseObject(origin: O, destination: Class<D>): D {
        return mapper.map(origin, destination)
    }

    fun <O,D> parseListObject(origin: List<O>, destination: Class<D>): List<D> {
        val destinationObjects: MutableList<D> = mutableListOf()
        origin.forEach { o -> destinationObjects.add(mapper.map(o, destination)) }
        return destinationObjects
    }
}
package marcelodev.comu_carona.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import marcelodev.comu_carona.models.Example
import marcelodev.comu_carona.v1.ExampleVO

class DozerMapperTest {

    private var inputObject: MockExample? = null

    @BeforeEach
    fun setUp() {
        inputObject = MockExample()
    }

    @Test
    fun parseEntityToVOTest() {
        val output: ExampleVO = DozerMapper.parseObject(inputObject!!.mockEntity(), ExampleVO::class.java)
        assertEquals(0, output.id)
        assertEquals("First Name Test0", output.name)
    }

    @Test
    fun parseEntityListToVOListTest() {
        val outputList: List<ExampleVO> =
            DozerMapper.parseListObject(inputObject!!.mockEntityList(), ExampleVO::class.java)

        val outputZero: ExampleVO = outputList[0]

        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.name)

        val outputSeven: ExampleVO = outputList[7]
        assertEquals(7.toLong(), outputSeven.id)
        assertEquals("First Name Test7", outputSeven.name)

        val outputTwelve: ExampleVO = outputList[12]
        assertEquals(12.toLong(), outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.name)
    }

    @Test
    fun parseVOToEntityTest() {

        val output: Example = DozerMapper.parseObject(inputObject!!.mockVO(), Example::class.java)

        assertEquals(0, output.id)
        assertEquals("First Name Test0", output.name)
    }

    @Test
    fun parserVOListToEntityListTest() {

        val outputList: List<Example> = DozerMapper.parseListObject(inputObject!!.mockVOList(), Example::class.java)

        val outputZero: Example = outputList[0]
        assertEquals(0, outputZero.id)
        assertEquals("First Name Test0", outputZero.name)

        val outputSeven: Example = outputList[7]
        assertEquals(7, outputSeven.id)
        assertEquals("First Name Test7", outputSeven.name)

        val outputTwelve: Example = outputList[12]
        assertEquals(12, outputTwelve.id)
        assertEquals("First Name Test12", outputTwelve.name)
    }
}
package marcelodev.comu_carona.mapper

import teste_spring.teste.data.vo.v1.ExampleVO
import marcelodev.comu_carona.models.Example
import java.util.ArrayList

class MockExample {
    fun mockEntity(): Example {
        return mockEntity(0)
    }

    fun mockVO(): ExampleVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Example> {
        val examples: ArrayList<Example> = ArrayList<Example>()
        for (i in 0..13) {
            examples.add(mockEntity(i))
        }
        return examples
    }

    fun mockVOList(): ArrayList<ExampleVO> {
        val examples: ArrayList<ExampleVO> = ArrayList()
        for (i in 0..13) {
            examples.add(mockVO(i))
        }
        return examples
    }

    fun mockEntity(number: Int): Example {
        val example = Example()
        example.id = number.toLong()
        example.name = "First Name Test$number"
        return example
    }

    fun mockVO(number: Int): ExampleVO {
        val example = ExampleVO()
        example.id = number.toLong()
        example.name = "First Name Test$number"
        return example
    }
}
import dev.timkante.dcp.multiplied
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll

class Task002KtTest : StringSpec({
    "task example 1 should pass" {
        listOf(1, 2, 3, 4, 5).multiplied shouldContainInOrder listOf(120, 60, 40, 30, 24)
    }

    "task example 2 should pass" {
        listOf(3, 2, 1).multiplied shouldContainInOrder listOf(2, 3, 6)
    }

    "lists with sizes of less than 2 do return themselves" {
        checkAll(Arb.list(gen = Arb.int(), range = 0..1)) { list ->
            list.multiplied shouldBe list
        }
    }

    "list of ints gets converted correctly" {
        checkAll(Arb.list(gen = Arb.int(), range = 2..15)) { list ->
            list.multiplied.withIndex().forEach { (index, convertedListElement) ->
                convertedListElement shouldBe list
                    .filterIndexed { elementIndex, _ -> elementIndex != index }
                    .reduce(Int::times)
            }
        }
    }
})
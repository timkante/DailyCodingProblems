import dev.timkante.dcp.containsSumOf
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll

class Task001KtTest : StringSpec({

    "example provided by task" {
        listOf(10, 15, 3, 7) containsSumOf 17 shouldBe true
    }

    "lists with less than two elements can never reach sum" {
        checkAll(Arb.list(gen = Arb.int(), range = 0..1), Arb.int()) { numbers, target ->
            withClue("<$numbers> contains two numbers that add up to <$target>") {
                numbers containsSumOf target shouldBe false
            }
        }
    }

    "sum of exactly two list elements as target yields true" {
        checkAll(Arb.list(gen = Arb.int(), range = 2..2)) { numbers ->
            val target = numbers.sum()
            withClue("<$numbers> add up to <$target>") {
                numbers containsSumOf target shouldBe true
            }
        }
    }

    "sum of two random list elements as target yields true" {
        checkAll(Arb.list(gen = Arb.int(), range = 2..15)) { numbers ->
            val target = numbers.asSequence().distinct().shuffled().take(2).sum()
            withClue("<$numbers> contains two elements that add up to <$target>") {
                numbers containsSumOf target shouldBe true
            }
        }
    }
})

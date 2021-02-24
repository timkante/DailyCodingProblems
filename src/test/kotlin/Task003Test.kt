import dev.timkante.dcp.deserialize
import dev.timkante.dcp.serialize
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class Task003Test : BehaviorSpec({

    listOf(
        "E",
        "root left E E right E E",
        "root left left.left E E E right E E",
        "sOmE oThEr vAluEs E E E hErE E E",
        "root left left.left left.left.left E E E E E",
    ).forEach { serialTree ->

        given(""""$serialTree" as serial value of a tree""") {

            `when`("deserializing a tree from it") {
                serialTree.deserialize().let { tree ->

                    then("it can be deserialized to the original value") {
                        tree.serialize() shouldBe serialTree
                    }

                    then("it should contain all the labels of the serial value"){
                        serialTree.split(" ").filterNot { label -> label == "E" }.onEach { label ->
                            tree.contains(label) shouldBe true
                        }
                    }

                    then("it should have the same amount of nodes as the serial value had labels") {
                        tree.size shouldBeExactly serialTree.split(" ").filterNot { label -> label == "E" }.size
                    }
                }
            }
        }
    }
})

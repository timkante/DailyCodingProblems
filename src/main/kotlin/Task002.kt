package dev.timkante.dcp

val List<Int>.multiplied: List<Int>
    get() = when (size) {
        in 0..1 -> this
        else -> indices.map { elementIndex ->
            asSequence().filterIndexed { multiplierIndex, _ -> multiplierIndex != elementIndex }.reduce(Int::times)
        }
    }
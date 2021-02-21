package dev.timkante.dcp

infix fun List<Int>.containsSumOf(target: Int): Boolean {
    fold(emptySet<Int>()) { complements, element ->
        when {
            complements.contains(element) -> return true
            else -> complements + (target - element)
        }
    }
    return false
}

package dev.timkante.dcp.helpers

import kotlin.collections.ArrayDeque
import kotlin.math.max

sealed class Tree {
    abstract val size: Int
    abstract val height: Int
    abstract fun isEmpty(): Boolean

    private object Empty : Tree() {
        override fun isEmpty(): Boolean = true
        override fun toString(): String = "E"
        override val size: Int = 0
        override val height: Int = -1
    }

    private data class T(
        val left: Tree,
        val value: String,
        val right: Tree,
    ) : Tree() {
        override fun isEmpty(): Boolean = false
        override fun toString(): String = "$value $left $right"
        override val size: Int = 1 + left.size + right.size
        override val height: Int = 1 + max(left.height, right.height)
    }

    operator fun plus(element: String): Tree = when (this) {
        Empty -> T(Empty, element, Empty)
        is T -> when {
            element < value -> T(left + element, value, right)
            element > value -> T(left, value, right + element)
            else -> T(left, element, right)
        }
    }

    fun contains(element: String): Boolean = when (this) {
        is Empty -> false
        is T -> element == value || left.contains(element) || right.contains(element)
    }

    companion object Factory {
        operator fun invoke(): Tree = Empty
        operator fun invoke(vararg elements: String): Tree = elements.fold(Empty) { tree: Tree, element: String ->
            tree.plus(element)
        }

        operator fun invoke(elements: List<String>): Tree = elements.fold(Empty) { tree: Tree, element: String ->
            tree.plus(element)
        }

        fun fromString(source: String): Tree = fromString(ArrayDeque(source.split(" ")))
        private fun fromString(sourceParts: ArrayDeque<String>): Tree = when (val value = sourceParts.removeFirst()) {
            "$Empty" -> Empty
            else -> T(fromString(sourceParts), value, fromString(sourceParts))
        }
    }
}

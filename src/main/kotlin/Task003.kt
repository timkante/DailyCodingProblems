package dev.timkante.dcp

import dev.timkante.dcp.helpers.Tree

fun String.deserialize(): Tree = Tree.fromString(this)
fun Tree.serialize(): String = "$this"

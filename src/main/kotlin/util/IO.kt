package util

fun readText(path: String): String = object {}.javaClass.getResource(path)?.readText()
    ?: throw IllegalArgumentException("Could not read file '$path'!")

fun readTextLines(path: String): List<String> = readText(path).lines()
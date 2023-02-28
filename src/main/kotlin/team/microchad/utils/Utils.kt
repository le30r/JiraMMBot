package team.microchad.utils

fun String.toUrl(): String =
    replace(" ","%20")
        .replace("\"", "%22")

fun String.deleteDoubleSpace(): String =
    replace("\\s+", " ")

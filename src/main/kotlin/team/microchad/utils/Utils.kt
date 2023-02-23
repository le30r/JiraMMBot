package team.microchad.utils

fun String.toUrl(): String =
    replace(" ","%20")
        .replace("\"", "%22")
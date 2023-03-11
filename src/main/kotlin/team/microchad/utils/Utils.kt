package team.microchad.utils

fun String.toUrlForm(): String =
    replace(" ", "%20")
        .replace("\"", "%22")
        .replace("?", "%3F")
        .replace(".", "%2E")

fun String.deleteDoubleSpace(): String =
    replace("\\s+", " ")

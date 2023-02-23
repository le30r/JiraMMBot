package team.microchad.utils

infix fun String.toUrl(string: String): String =
    string.replace(" ","%20").replace("\"", "%22")
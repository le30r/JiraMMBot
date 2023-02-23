package team.microchad.utils

infix fun String.toUrl(string: String) =
    string.replace(" ","%20").replace("\"", "%22")
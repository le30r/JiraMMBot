package team.microchad.dto.mm
import kotlinx.serialization.Serializable
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class User(val id: String, val username: String)

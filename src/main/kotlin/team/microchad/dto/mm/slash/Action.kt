package team.microchad.dto.mm.slash

import kotlinx.serialization.Serializable

@Serializable
data class Action(
   val id: String,
   val name: String,
   val integration: Integration
)

@Serializable
data class ActionResponse(
   val ephemeralText: String
)


//{
//  "update": {
//    "message": "Updated!",
//    "props": {}
//  },
//  "ephemeral_text": "You updated the post!"
//}
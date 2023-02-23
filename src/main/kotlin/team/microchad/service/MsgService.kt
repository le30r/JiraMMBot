package team.microchad.service

import team.microchad.dto.mm.IncomingMsg

class MsgService (message: IncomingMsg){
    private var message = message;

    fun getStatus(): String {
        return message.text.replace("stats ", "")
    }
}

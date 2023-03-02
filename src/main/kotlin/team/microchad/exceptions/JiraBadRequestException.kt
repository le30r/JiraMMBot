package team.microchad.exceptions

import org.apache.http.HttpException

class JiraBadRequestException(message: String) : HttpException(message)
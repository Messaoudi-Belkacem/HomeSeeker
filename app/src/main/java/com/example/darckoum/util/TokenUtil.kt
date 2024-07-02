package com.example.darckoum.util

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import java.util.Date

object TokenUtil {

    private fun decodeTokenAndExtractExpirationDate(token: String): Date? {
        try {
            val decodedToken = JWT.decode(token)
            return decodedToken.expiresAt
        } catch (e: JWTDecodeException) {
            e.printStackTrace()
        }
        return null
    }

    fun isTokenExpired(token: String?): Boolean {
        if (token == null) {
            return true
        } else {
            val expirationDate = decodeTokenAndExtractExpirationDate(token)
            return expirationDate != null && expirationDate.before(Date())
        }
    }

}
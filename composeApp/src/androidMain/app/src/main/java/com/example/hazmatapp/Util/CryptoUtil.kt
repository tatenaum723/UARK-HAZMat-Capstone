package com.example.hazmatapp.Util

import org.kotlincrypto.macs.hmac.sha2.HmacSHA256
import java.security.SecureRandom

class CryptoUtil {
    // Create HMAC key
    fun CreateKey(): ByteArray {
        val random = SecureRandom()
        val bytes = ByteArray(20)
        random.nextBytes(bytes)
        return bytes
    }

    // Check HMAC values
    fun checkHMAC(data: ByteArray, hashVal: ByteArray): Boolean {
        // Initialize values and variables
        var verified = false
        val key = CreateKey();
        val mac = HmacSHA256(key)

        // Create HMAC value with received data
        val hashData = mac.doFinal(data)

        // Compare hashVal with hashData
        if (hashData.contentEquals(hashVal)) {
            verified = true
        }

        return verified
    }

}
package com.example.hazmatapp

import com.example.hazmatapp.Util.CryptoUtil
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256

class CryptoUtilTest {

    @Test
    fun testCreateKey() {
        val cryptoUtil = CryptoUtil()

        val key1 = cryptoUtil.CreateKey()
        assertNotNull(key1)
        assertTrue(key1.size == 20)

        // Ensure that multiple calls to CreateKey() generate different keys
        val key2 = cryptoUtil.CreateKey()
        assertFalse(key1.contentEquals(key2))
    }

    @Test
    fun testCheckHMAC() {
        val cryptoUtil = CryptoUtil()

        // Mock data
        val key = "testkey".toByteArray()
        val data = "testdata".toByteArray()

        // Generate hash value using the real implementation
        val hashVal = HmacSHA256(key).doFinal(data)

        // Test with correct hash value
        assertTrue(cryptoUtil.checkHMAC(key, data, hashVal))

        // Test with incorrect hash value
        val fakeHashVal = "fakehash".toByteArray()
        assertFalse(cryptoUtil.checkHMAC(key, data, fakeHashVal))
    }
}
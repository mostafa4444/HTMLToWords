package com.example.htmlwords.network

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.htmlwords.repository.remote.RemoteRepositoryImpl
import com.example.htmlwords.utils.AppConstants
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.*
import java.util.concurrent.TimeUnit


class NetworkUtilityTest {

    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
        @Rule get


    private val NETWORK_CALL_URL = "https://instabug.com/"

    lateinit var webMockWebServer: MockWebServer

    @Before
    fun setUp() {
        webMockWebServer = MockWebServer()
        webMockWebServer.start()
    }

    @After
    fun tearDown() {
        webMockWebServer.shutdown()
    }


    @Test
    fun fetchHtmlWebRequest() {
        val result = NetworkUtility.fetchHtmlWenRequest(NETWORK_CALL_URL)
        webMockWebServer.takeRequest(5L, TimeUnit.SECONDS) /* We tell our server to block for 10 second before continue the test and
                After 10 second we assume the the our resource will return notnull data
         */
        assertNotNull(result)
    }
}
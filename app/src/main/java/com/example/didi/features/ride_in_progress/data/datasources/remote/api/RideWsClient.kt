package com.example.didi.features.ride_in_progress.data.datasources.remote.api

import android.util.Log
import com.example.didi.features.ride_in_progress.data.datasources.remote.mapper.toDomain
import com.example.didi.features.ride_in_progress.data.datasources.remote.models.RideEventDto
import com.example.didi.features.ride_in_progress.data.datasources.remote.models.RideStartMessageDto
import com.example.didi.features.ride_in_progress.domain.entities.RideEvent
import com.google.gson.Gson
import okhttp3.*

class RideWsClient(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson = Gson()
) {
    private var ws: WebSocket? = null

    fun connect(
        url: String,
        startMessage: RideStartMessageDto,
        onEvent: (RideEvent) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val request = Request.Builder().url(url).build()

        ws = okHttpClient.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(webSocket: WebSocket, response: Response) {
                    val payload = gson.toJson(startMessage)
                    Log.d("RideWsClient", "WS open -> send: $payload")
                    webSocket.send(payload)
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    try {
                        Log.d("RideWsClient", "WS message: $text")
                        val dto = gson.fromJson(text, RideEventDto::class.java)
                        onEvent(dto.toDomain())
                    } catch (e: Exception) {
                        onError(e)
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    Log.e("RideWsClient", "WS failure", t)
                    onError(t)
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    webSocket.close(code, reason)
                }
            }
        )
    }

    fun disconnect() {
        ws?.close(1000, "closed by client")
        ws = null
    }
}
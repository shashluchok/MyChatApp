package com.example.android.mychatapp

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isNeedToSend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*val client = HttpClient(CIO) {
            install(WebSockets)
            engine {
                requestTimeout = 100000L
            }
        }
        GlobalScope.launch {

            try {
                client.ws(
                     method = HttpMethod.Get,
                     host = "myveryfirstappheroku.herokuapp.com",
                      path = "/chat"
                ) {
                    val messageOutputRoutine = launch { outputMessages() }
                    val userInputRoutine = launch { inputMessages() }

                    userInputRoutine.join()
                    messageOutputRoutine.cancelAndJoin()
                }
            } catch (e: java.lang.Exception) {
                Log.v("Zhoppa", "${e}")
                client.close()
                Log.v("Zhoppa", "Connection closed. Goodbye!")
            }
        }

        cardView.setOnClickListener {
            if (!editText.text.isNullOrEmpty()) {

                isNeedToSend = true
            }
        }
*/

    }

    /*suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                chat_messages_tv.text = chat_messages_tv.text.toString() + (message.readText().toString() + "\n")
                println(message.readText())
            }
        } catch (e: Exception) {
            Log.v("Zhoppa", "Error while receiving: " + e.localizedMessage)
        }
    }

    suspend fun DefaultClientWebSocketSession.inputMessages() {
        while (true) {
            if (isNeedToSend) {
                isNeedToSend = false
                try {
                    send(editText.text.toString())
                } catch (e: Exception) {
                    Log.v("Zhoppa", "Error while sending: " + e.localizedMessage)
                    return
                }
                editText.text.clear()
            }
        }
    }*/

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
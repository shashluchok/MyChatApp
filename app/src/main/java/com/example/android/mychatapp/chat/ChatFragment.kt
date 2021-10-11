package com.example.android.mychatapp.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.mychatapp.R
import com.example.android.mychatapp.base.BaseFragment
import com.google.android.material.transition.MaterialContainerTransform
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.*

class ChatFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_chat

    private lateinit var adapter: ChatMessagesAdapter

    private var isNeedToSend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  val transform = MaterialContainerTransform()
        transform.duration = 500
        sharedElementEnterTransition = transform*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chat_messages_rv.layoutManager = LinearLayoutManager(requireActivity())
        adapter = ChatMessagesAdapter(mContext = requireActivity())
        chat_messages_rv.adapter = adapter

        chat_message_send_cv.setOnClickListener {
            if (!chat_message_input_et.text.isNullOrEmpty()) {
                isNeedToSend = true
            }
        }
        connectToChat()

    }

    private fun connectToChat() {
        val client = HttpClient(CIO) {
            install(WebSockets)
            engine {
                requestTimeout = 100000L
            }
        }
        lifecycleScope.launch (Dispatchers.IO){

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
                e.printStackTrace()
                //...
                adapter.addMesage(getString(R.string.connection_lost))
                client.close()
            }
        }
    }

   suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val text = message.readText().toString()
                withContext(Dispatchers.Main){
                    adapter.addMesage(text)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //...
        }
    }

    suspend fun DefaultClientWebSocketSession.inputMessages() {
        while (true) {
            if (isNeedToSend) {
                isNeedToSend = false
                try {
                    var message = ""
                    withContext(Dispatchers.Main){
                        message = chat_message_input_et.text.toString()
                    }
                    send(message)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireActivity(),getString(R.string.error_sending_message),Toast.LENGTH_SHORT)
                    }
                    e.printStackTrace()
                    return
                }
                chat_message_input_et.text?.clear()
            }
        }
    }


}
package com.example.android.mychatapp.customviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_auth.*

class PreImeEditText : androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?) : super(context!!) {}

    private lateinit var onKeyPreImePressed:()->Unit

    fun setUpOnKeyPreImePressedCallback(callback:()->Unit){
        onKeyPreImePressed = callback
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
//            onKeyPreImePressed.invoke()
            val imm: InputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(rootView.windowToken, 0)
            this.clearFocus()
            return true
        }
        return super.onKeyPreIme(keyCode, event)
    }
}
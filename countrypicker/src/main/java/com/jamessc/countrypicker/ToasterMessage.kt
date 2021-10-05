package com.jamessc.countrypicker

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

class ToasterMessage {

    companion object {
        fun showHelloWorld(c : Context) {
            Toast.makeText(c, "Hello World", LENGTH_LONG).show()

        }

    }


}
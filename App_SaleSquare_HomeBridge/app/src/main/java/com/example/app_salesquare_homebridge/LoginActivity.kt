package com.example.app_salesquare_homebridge

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val spannableString = SpannableString("No tengo cuenta. Registrarme")

        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity,  PostResultsActivity::class.java) //Coloca el RegisterActivity
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = resources.getColor(R.color.blue, theme)
            }
        }

        spannableString.setSpan(clickableSpan, 16, 28, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvRegister.text = spannableString

        tvRegister.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}
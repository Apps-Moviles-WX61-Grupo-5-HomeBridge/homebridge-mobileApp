package     com.example.app_salesquare_homebridge

import      android.content.Intent
import      android.os.Bundle
import android.os.Handler
import      android.text.SpannableString
import      android.text.TextPaint
import      android.text.style.ClickableSpan
import      android.util.Log
import      android.view.View
import      okhttp3.*
import      android.widget.TextView
import      android.widget.Toast
import      androidx.appcompat.app.AppCompatActivity
import      com.example.app_salesquare_homebridge.shared.user.UserWrapper
import      okhttp3.MediaType.Companion.toMediaType
import      okio.IOException
import      org.json.JSONObject
import retrofit2.http.Headers


public final class LoginActivity: AppCompatActivity() {
    private val client = OkHttpClient()
    private val m_UserWrapper: UserWrapper = UserWrapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val btnLogin = findViewById<TextView>(R.id.btLogin)
        val etEmail = findViewById<TextView>(R.id.etEmail)
        val etPassword = findViewById<TextView>(R.id.etPassword)
        val spannableString = SpannableString("No tengo cuenta. Registrarme")

        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity,  RegisterActivity::class.java)
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

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor, ingrese su correo y contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                login(email, password)
            }
        }

    }

    private fun login(email: String, password: String) {
        val url = "https://salesquare-aceeh0btd8frgyc2.brazilsouth-01.azurewebsites.net/api/v1/user/login"
        val Json = JSONObject().apply {
            put("password", password)
            put("email", email)
        }
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), Json.toString())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("LoginActivity", "Error de conexión ${e.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error de conexión ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val responseBody = it.string()

                    runOnUiThread{
                        try {
                            if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                                val jsonObject = JSONObject(responseBody)
                                val result = jsonObject.optBoolean("result", false)
                                if (result) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Inicio de sesión exitoso",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    this@LoginActivity.m_UserWrapper.token(jsonObject.getString("token"))
                                    this@LoginActivity.m_UserWrapper.userId(jsonObject.getInt("userId"))
                                    this@LoginActivity.m_UserWrapper.refreshToken(jsonObject.getString("refreshToken"))

                                    val intent =
                                        Intent(this@LoginActivity, PostResultsActivity::class.java)
                                    intent.putExtra("userWrapper", this@LoginActivity.m_UserWrapper)
                                    startActivity(intent)

                                }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Email o contraseña incorrectos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            val a: Int = 0
                        }
                    }
                }
            }
        })
    }
}
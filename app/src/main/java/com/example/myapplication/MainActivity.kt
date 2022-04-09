package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        user?.let {
            val ExEmail = user.email
            button_login.setOnClickListener {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
                val Email: String = Text_Email.text.toString()
//                Toast.makeText(baseContext, ExEmail, Toast.LENGTH_SHORT).show()
                if(ExEmail != Email){
                    auth.createUserWithEmailAndPassword(
                        Email,
                        Text_Password.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                                setContentView(R.layout.activity_main)
                                Hello.text = "Hello,"+Email
                                Hello.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat())
                                changePassword.setOnClickListener {
                                    startActivity(Intent(this,ChangePassword::class.java))
                                }
                            } else {
                                Toast.makeText(baseContext, "failed. Please try again.",
                                    Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
                }else{
                    auth.signInWithEmailAndPassword(Email,
                        Text_Password.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                                setContentView(R.layout.activity_main)
                                Hello.text = "Hello,"+Email
                                Hello.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat())
                                changePassword.setOnClickListener {
                                    startActivity(Intent(this,ChangePassword::class.java))
                                }
                            } else {
                                Toast.makeText(baseContext, "failed. Please try again.",
                                    Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
                }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}
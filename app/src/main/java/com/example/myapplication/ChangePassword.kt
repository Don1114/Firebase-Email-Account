package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.password.*

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val user = Firebase.auth.currentUser
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password)
        submit.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
            user!!.updatePassword(new_Password.text.toString())
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful && new_Password.text.toString() == Password_again.text.toString()){
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else{
                        Toast.makeText(baseContext, "failed. Please try again.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}
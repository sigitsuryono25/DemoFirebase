package com.lauwba.surelabs.demofirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.onClick {
            if (email.text.toString().isEmpty()) {
                email.error = "Isilah, kalo belum punya daftarlah"
            } else if (password.text.toString().isEmpty()) {
                password.error = "Isilah,!"
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            toast("Login Berhasil")
                        } else {
                            toast("Login Gagal")
                        }
                    }
            }
        }

        register.onClick {
            startActivity<MainActivity>()
        }
    }
}

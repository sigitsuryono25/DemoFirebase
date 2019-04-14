package com.lauwba.surelabs.demofirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pixplicity.easyprefs.library.Prefs
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
                            getDataFromDatabase(email.text.toString())
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

    private fun getDataFromDatabase(email: String) {
        val ref = FirebaseDatabase.getInstance().getReference("user")
        ref.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    try {
                        for (issue in p0.children) {

                            //ini untuk ambil data dari masing masing node yang ada
                            //di firebase
                            val result = issue.getValue(User::class.java)

                            //yang ini untuk set session
                            Prefs.putString("email", result?.email)
                            Prefs.putString("nama", result?.nama)
                            Prefs.putString("jk", result?.jk)

                            //pindah ke halaman dasboard
                            startActivity<DashboardActivity>()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            })

        //SELECT * FROM user WHERE email = "$email"

    }
}

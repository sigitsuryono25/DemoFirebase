package com.lauwba.surelabs.demofirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivity : AppCompatActivity() {

    private var jk = arrayListOf("Laki-Laki", "Perempuan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDataJk()
        initView()
    }

    private fun initView() {
        daftar.onClick {
            if (emailKamu.text.toString().isEmpty()) {
                emailKamu.error = "Isi woy. WAJIB !"
            } else if (password.text.toString().isEmpty()) {
                password.error = "Isi Juga Woy. Wajib !"
            } else if (nama.text.toString().isEmpty()) {
                nama.error = "Isi woy. Masak nggak punya nama !!!"
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailKamu.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        insertIntoDatabase()
                    }
                }
            }
        }
    }

    private fun insertIntoDatabase() {
        val ref = FirebaseDatabase.getInstance().getReference("user")
        val user = User()
        user.email = emailKamu.text.toString()
        user.jk = jenisKelamin.selectedItem.toString()
        user.nama = nama.text.toString()

        val key = ref.push().key
        key?.let { ref.child(it).setValue(user) }?.addOnCompleteListener {
            if (it.isSuccessful) {
                alert {
                    message = "Registrasi berhasil"
                    okButton { }
                }.show()
            }
        }
    }

    private fun initDataJk() {
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, jk)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        jenisKelamin.adapter = adapter
    }
}

package com.lauwba.surelabs.demofirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        nama.text = Prefs.getString("nama", "")
        jk.text = Prefs.getString("jk", "")
        email.text = Prefs.getString("email", "")

        editnama.onClick {
            val ref = FirebaseDatabase.getInstance().getReference("user")
            ref.orderByChild("email").equalTo(email.text.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try {
                            for (issues in p0.children) {
                                val key = issues.key
                                updateNama(key, namaKamuYangBaru.text.toString())
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
        }

        logout.onClick {
            Prefs.clear()
            finish()
            startActivity<LoginActivity>()
        }
    }

    private fun updateNama(key: String?, newName: String) {
        val ref = FirebaseDatabase.getInstance().getReference("user")

        //ini proses update nama
        key?.let { ref.child(it).child("nama").setValue(newName) }?.addOnCompleteListener {
            if (it.isSuccessful) {
                alert {
                    message = "Update Nama berhasil gan"
                    okButton { }
                }.show()
            }
        }

    }
}

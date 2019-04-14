package com.lauwba.surelabs.demofirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        nama.text = Prefs.getString("nama", "")
        jk.text = Prefs.getString("jk", "")
        email.text = Prefs.getString("email", "")
    }
}

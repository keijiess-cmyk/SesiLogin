package com.example.sesisilogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        session = SessionManager(this)

        // =================================================
        // PENGAMAN DASHBOARD
        // Jika tidak ada data profil, kembali ke Login
        // =================================================

        if (!session.adaDataProfil()) {
            kembaliKeLogin()
            return
        }

        // =================================================
        // MENAMPILKAN DATA PROFIL DARI SESSION
        // =================================================

        findViewById<TextView>(R.id.tvNama).text =
            session.ambilNama()

        findViewById<TextView>(R.id.tvEmail).text =
            session.ambilEmail()

        findViewById<TextView>(R.id.tvWaktu).text =
            formatWaktu(session.ambilWaktuLogin())

        // =================================================
        // TOMBOL KELUAR
        // =================================================

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            keluar()
        }
    }

    // =====================================================
    // FORMAT WAKTU LOGIN
    // =====================================================

    private fun formatWaktu(millis: Long): String {

        if (millis == 0L) {
            return "-"
        }

        val format = SimpleDateFormat(
            "dd MMM yyyy, HH:mm",
            Locale("id", "ID")
        )

        return format.format(Date(millis))
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    private fun keluar() {

        // Hapus seluruh data sesi
        session.hapusSesi()

        // Kembali ke Login
        kembaliKeLogin()
    }

    // =====================================================
    // KEMBALI KE LOGIN
    // =====================================================

    private fun kembaliKeLogin() {

        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )

        // Tutup Dashboard
        finish()
    }
}
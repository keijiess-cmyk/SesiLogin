package com.example.sesisilogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    companion object {

        private const val EMAIL_DEMO =
            "rani@example.com"

        private const val SANDI_DEMO =
            "123456"
    }

    private lateinit var session: SessionManager

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var cbIngatSaya: CheckBox
    private lateinit var tvPesanMasuk: TextView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_login
        )

        session = SessionManager(this)

        // =================================================
        // HUBUNGKAN VIEW
        // =================================================

        etEmail =
            findViewById(R.id.etEmail)

        etPassword =
            findViewById(R.id.etPassword)

        cbIngatSaya =
            findViewById(R.id.cbIngatSaya)

        tvPesanMasuk =
            findViewById(R.id.tvPesanMasuk)

        // =================================================
        // CEK AUTO LOGIN
        // =================================================

        if (session.sudahLogin()) {

            if (session.sesiMasihBerlaku()) {

                bukaDashboard()

                return

            } else {

                // Sesi lebih dari 7 hari
                session.hapusSesi()
            }
        }

        // =================================================
        // EMAIL LAMA
        // =================================================

        etEmail.setText(
            session.ambilEmail()
        )

        // =================================================
        // TOMBOL MASUK
        // =================================================

        findViewById<Button>(
            R.id.btnMasuk
        ).setOnClickListener {

            prosesLogin()
        }
    }

    // =====================================================
    // PROSES LOGIN
    // =====================================================

    private fun prosesLogin() {

        val email =
            etEmail.text
                .toString()
                .trim()

        val sandi =
            etPassword.text
                .toString()

        tvPesanMasuk.text = ""

        // =================================================
        // VALIDASI EMAIL
        // =================================================

        if (email.isEmpty()) {

            etEmail.error =
                getString(
                    R.string.pesan_email_kosong
                )

            return
        }

        // =================================================
        // VALIDASI SANDI
        // =================================================

        if (sandi.length < 6) {

            etPassword.error =
                getString(
                    R.string.pesan_sandi_pendek
                )

            return
        }

        // =================================================
        // CEK AKUN DEMO
        // =================================================

        if (
            email != EMAIL_DEMO ||
            sandi != SANDI_DEMO
        ) {

            tvPesanMasuk.text =
                getString(
                    R.string.pesan_login_gagal
                )

            return
        }

        // =================================================
        // BUAT NAMA DARI EMAIL
        // =================================================

        val nama =
            email
                .substringBefore("@")
                .replaceFirstChar {
                    it.uppercase()
                }

        // =================================================
        // SIMPAN SESI
        // =================================================

        session.simpanSesi(
            nama = nama,
            email = email,
            ingatSaya = cbIngatSaya.isChecked
        )

        // =================================================
        // BUKA DASHBOARD
        // =================================================

        bukaDashboard()
    }

    // =====================================================
    // PINDAH KE DASHBOARD
    // =====================================================

    private fun bukaDashboard() {

        val intent =
            Intent(
                this,
                DashboardActivity::class.java
            )

        startActivity(intent)

        finish()
    }
}
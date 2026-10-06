package com.example.sesisilogin

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            NAMA_PREFS,
            Context.MODE_PRIVATE
        )

    companion object {

        private const val NAMA_PREFS = "sesi_login"

        private const val KEY_SUDAH_LOGIN = "sudah_login"
        private const val KEY_NAMA = "nama"
        private const val KEY_EMAIL = "email"
        private const val KEY_WAKTU = "waktu_login"

        // =================================================
        // BATAS SESI 7 HARI
        // =================================================

        private const val BATAS_7_HARI =
            7L * 24L * 60L * 60L * 1000L
    }

    // =====================================================
    // MENYIMPAN SESI
    // =====================================================

    fun simpanSesi(
        nama: String,
        email: String,
        ingatSaya: Boolean
    ) {

        prefs.edit()
            .putBoolean(
                KEY_SUDAH_LOGIN,
                ingatSaya
            )
            .putString(
                KEY_NAMA,
                nama
            )
            .putString(
                KEY_EMAIL,
                email
            )
            .putLong(
                KEY_WAKTU,
                System.currentTimeMillis()
            )
            .apply()
    }

    // =====================================================
    // CEK APAKAH USER BOLEH AUTO-LOGIN
    // =====================================================

    fun sudahLogin(): Boolean {

        return prefs.getBoolean(
            KEY_SUDAH_LOGIN,
            false
        )
    }

    // =====================================================
    // CEK SESI MASIH BERLAKU ATAU TIDAK
    // =====================================================

    fun sesiMasihBerlaku(): Boolean {

        val waktuLogin = ambilWaktuLogin()

        if (waktuLogin == 0L) {
            return false
        }

        val sekarang = System.currentTimeMillis()

        val selisih =
            sekarang - waktuLogin

        return selisih <= BATAS_7_HARI
    }

    // =====================================================
    // CEK DATA PROFIL
    // =====================================================

    fun adaDataProfil(): Boolean {

        return ambilNama().isNotEmpty() &&
                ambilEmail().isNotEmpty()
    }

    // =====================================================
    // AMBIL NAMA
    // =====================================================

    fun ambilNama(): String {

        return prefs.getString(
            KEY_NAMA,
            ""
        ) ?: ""
    }

    // =====================================================
    // AMBIL EMAIL
    // =====================================================

    fun ambilEmail(): String {

        return prefs.getString(
            KEY_EMAIL,
            ""
        ) ?: ""
    }

    // =====================================================
    // AMBIL WAKTU LOGIN
    // =====================================================

    fun ambilWaktuLogin(): Long {

        return prefs.getLong(
            KEY_WAKTU,
            0L
        )
    }

    // =====================================================
    // HAPUS SELURUH SESI
    // =====================================================

    fun hapusSesi() {

        prefs.edit()
            .clear()
            .apply()
    }
}
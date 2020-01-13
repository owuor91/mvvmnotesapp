package io.owuor91.authentication.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import io.owuor91.authentication.R
import io.owuor91.authentication.viewmodel.AuthViewModel
import io.owuor91.mvvmnotesapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity() {
    private val authViewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observeLoginLiveData()
    }

    fun clickLogin(view: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toastMessage(getString(R.string.auth_validation))
            return
        }
        authViewModel.loginUser(email, password)
    }

    fun observeLoginLiveData() {
        authViewModel.getAuthResponse().observe(this, Observer { authResponse ->
            toastMessage(getString(R.string.login_success))
            authViewModel.saveUserDetails(authResponse.authToken, authResponse.userId)
            startActivity(Intent(baseContext, DashboardActivity::class.java))
        })

        authViewModel.getAuthError().observe(this, Observer { error ->
            toastMessage(error)
        })
    }

    fun clickRegister(view: View) {
        startActivity(Intent(baseContext, RegisterActivity::class.java))
    }
}

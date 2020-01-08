package io.owuor91.authentication.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import io.owuor91.authentication.R
import io.owuor91.authentication.di.authmodule
import io.owuor91.authentication.viewmodel.RegisterViewModel
import io.owuor91.mvvmnotesapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

private val loadAuthModule by lazy {
    loadKoinModules(authmodule)
}

private fun injectAuthModule() = loadAuthModule

class RegisterActivity : BaseActivity() {
    private val registerViewModel: RegisterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        injectAuthModule()
        observeRegistrationLiveData()
    }

    fun clickRegister(view: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            toastMessage(getString(R.string.auth_validation))
            return
        }

        registerViewModel.registerUser(email, password)
    }

    fun observeRegistrationLiveData() {
        registerViewModel.getAuthResponse().observe(this, Observer { authResponse ->
            toastMessage(getString(R.string.reg_success))
            registerViewModel.saveUserDetails(authResponse.authToken, authResponse.userId)
            startActivity(Intent(baseContext, DashboardActivity::class.java))
        })

        registerViewModel.getAuthError().observe(this, Observer { error ->
            toastMessage(error)
        })
    }
}

package com.company.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.company.task.R
import com.company.task.business.UserBusiness
import com.company.task.constants.TaskConstants
import com.company.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Instanciar as variáveis da classe
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListenners()

        isLoginExistent()
    }

    private fun setListenners() {
        buttonLogin.setOnClickListener(this)
        textView2.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                handleLogin()
            }
            R.id.textView2 -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }

    private fun isLoginExistent() {

        val id = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val email = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)

        if (id != "" && email != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editSenha.text.toString()

        if (mUserBusiness.login(email, password)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_incorreto), Toast.LENGTH_LONG).show()
        }
    }
}

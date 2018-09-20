package com.company.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.company.task.R
import com.company.task.business.UserBusiness
import com.company.task.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Eventos
        setListeners()

        //Instanciar as variáveis da classe
        mUserBusiness = UserBusiness(this)

    }

    private fun setListeners() {
        buttonCadastrar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonCadastrar -> {
                handleSave()
            }
        }
    }

    private fun handleSave() {

        try {

            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editSenha.text.toString()

            mUserBusiness.insert(name, email, password)

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_SHORT).show()
        }
        // Faz a inserção do usuário
    }
}

package com.company.task.business

import android.content.Context
import com.company.task.R
import com.company.task.constants.TaskConstants
import com.company.task.entities.UserEntity
import com.company.task.repository.UserRepository
import com.company.task.util.SecurityPreferences
import com.company.task.util.ValidationException

class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {

        val user: UserEntity? = mUserRepository.get(email, password)
        return if (user != null) {

            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            return true
        } else {
            return false
        }
    }

    fun insert(name: String, email: String, password: String) {

        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_nomes))
            }

            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_em_uso))
            }

            val userId = mUserRepository.insert(name, email, password)

            //Salvar dados do usuário no shared
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)

        } catch (e: Exception) {
            throw e
        }
    }
}

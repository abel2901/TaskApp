package com.company.task.business

import android.content.Context
import com.company.task.constants.TaskConstants
import com.company.task.entities.TaskEntity
import com.company.task.repository.TaskRepository
import com.company.task.util.SecurityPreferences

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mTaskRepository.get(id)

    fun getList(taskFilter: Int): MutableList<TaskEntity> {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userId, taskFilter)
    }

    //corpo
    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)

    fun update(taskEntity: TaskEntity) = mTaskRepository.update(taskEntity)

    fun delete(taskId: Int) = mTaskRepository.delete(taskId)

    fun complete(taskId: Int, complete: Boolean) {
        val task = mTaskRepository.get(taskId)
        if (task != null) {
            task.complete = complete
            mTaskRepository.update(task)
        }
    }
}

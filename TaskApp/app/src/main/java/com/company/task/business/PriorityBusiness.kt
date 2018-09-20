package com.company.task.business

import android.content.Context
import com.company.task.entities.PriorityEntity
import com.company.task.repository.PriorityRepository

class PriorityBusiness(context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}
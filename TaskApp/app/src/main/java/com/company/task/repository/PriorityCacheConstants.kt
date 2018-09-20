package com.company.task.repository

import com.company.task.entities.PriorityEntity

class PriorityCacheConstants private constructor() {

    companion object {

        fun setCache(list: List<PriorityEntity>) {
            for (item in list) {
                mPriorityEntity.put(item.id, item.description)
            }
        }

        fun getPriorityDescription(id: Int): String {
            if (mPriorityEntity[id] == null) {
                return ""
            }
            return mPriorityEntity[id].toString()
        }

        private val mPriorityEntity = hashMapOf<Int, String>()

        //Criar a listagem da prioridade para salvar na lista.


    }
}

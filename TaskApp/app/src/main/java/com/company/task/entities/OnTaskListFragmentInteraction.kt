package com.company.task.entities

interface OnTaskListFragmentInteraction {

    fun onListClick(taskId: Int) {

    }

    fun onDeleteClick(taskId: Int) {

    }

    fun onUncompleteClick(taskId: Int) {

    }

    fun onCompleteClick(taskId: Int) {

    }
}

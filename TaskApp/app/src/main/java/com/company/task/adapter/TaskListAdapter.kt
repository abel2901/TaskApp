package com.company.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.company.task.R
import com.company.task.entities.OnTaskListFragmentInteraction
import com.company.task.entities.TaskEntity
import com.company.task.viewholder.TaskViewHolder

class TaskListAdapter(val taskList: List<TaskEntity>, val listener: OnTaskListFragmentInteraction) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, context!!, listener)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }
}

package com.company.task.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.company.task.R
import com.company.task.entities.OnTaskListFragmentInteraction
import com.company.task.entities.TaskEntity
import com.company.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteraction) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDuedate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(task: TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDuedate.text = task.dueDate

        if (task.complete) {
            mImageTask.setImageResource(R.drawable.ic_done)
        }

//        mTextDescription.setOnClickListener(this)
//        finish()

        //Evento de click para a edição
        mTextDescription.setOnClickListener({
            listener.onListClick(task.id)
        })

        mTextDescription.setOnClickListener({
            showConfirmationDialog(task)
            true
        })

        mImageTask.setOnClickListener({
            if (task.complete) {
                listener.onUncompleteClick(task.id)
            } else {
                listener.onCompleteClick(task.id)
            }
        })
    }

    private fun showConfirmationDialog(task: TaskEntity) {
        //listener.onDeleteClick(taskId)
        AlertDialog.Builder(context)
                .setTitle("Remoção de tarefa")
                .setMessage("Deseja Remover ${task.description}?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Remover", { dialog, i -> listener.onDeleteClick(task.id) })
                .setNeutralButton("Cancelar", null).show()

    }

    private class handleRemoval(val listener: OnTaskListFragmentInteraction, val taskId: Int) : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            listener.onDeleteClick(taskId)
        }
    }
}

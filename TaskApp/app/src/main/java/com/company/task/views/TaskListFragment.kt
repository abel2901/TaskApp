package com.company.task.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.company.task.R
import com.company.task.adapter.TaskListAdapter
import com.company.task.business.TaskBusiness
import com.company.task.constants.TaskConstants
import com.company.task.entities.OnTaskListFragmentInteraction
import com.company.task.util.SecurityPreferences

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: OnTaskListFragmentInteraction
    private var mTaskFilter: Int = 0

    companion object {

        fun newInstance(taskFilter: Int): TaskListFragment {
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTaskFilter = arguments.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_task_list, container, false)

        //Classe anônima: é como se existisse uma classe em OnClickListener e implementa esse método
        rootView.findViewById<FloatingActionButton>(R.id.floatingAddTask).setOnClickListener(View.OnClickListener {
            startActivity(Intent(mContext, TaskFormActivity::class.java))
        })

        mContext = rootView.context
        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        mListener = object : OnTaskListFragmentInteraction {
            override fun onListClick(taskId: Int) {

                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(mContext, TaskFormActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                loadTasks()
                Toast.makeText(mContext, getString(R.string.tarefa_removida_sucesso), Toast.LENGTH_LONG).show()
            }

            override fun onUncompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, false)
                loadTasks()
            }

            override fun onCompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, true)
                loadTasks()
            }
        }

        // 1 - obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)


        // 2 - Definir um adapter com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListener)

        // 3 - Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.floatingAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }

        // Lembrando que o context no fragment não pode se utilizando o (this)
    }

    private fun isAutoHideEnabled(){

    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }
}

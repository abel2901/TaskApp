package com.company.task.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.company.task.R
import com.company.task.business.PriorityBusiness
import com.company.task.business.TaskBusiness
import com.company.task.constants.TaskConstants
import com.company.task.entities.PriorityEntity
import com.company.task.entities.TaskEntity
import com.company.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var SIMPLE_DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")

    private var mLstPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mLstPrioritiesId: MutableList<Int> = mutableListOf()
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        loadPriorities()
        loadDataFromActivity()
        setListeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonData -> {
                openDatePicker()
            }
            R.id.buttonTarefa -> {
                handleSave()
            }
        }
    }

    private fun setListeners() {
        buttonData.setOnClickListener(this)
        buttonTarefa.setOnClickListener(this)
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)

            val task = mTaskBusiness.get(mTaskId)
            if (task != null) {
                editTextDescription.setText(task.description)
                buttonData.text = task.dueDate
                checkbox.isChecked = task.complete
                spinner.setSelection(getIndex(task.priorityId))

                buttonTarefa.text = getString(R.string.atualizar_tarefa)

            }
        }
    }

    private fun handleSave() {

        try {

            //[3, 5, 8, 3, 7, 87, 2]
            val priorityID = mLstPrioritiesId[spinner.selectedItemPosition]
            val complete = checkbox.isChecked

            val dueDate = buttonData.text.toString()
            val description = editTextDescription.text.toString()
            val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

            //inicaliza
            val taskEntity = TaskEntity(mTaskId, userId, priorityID, description, dueDate, complete)

            if (mTaskId == 0) {
                mTaskBusiness.insert(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_incluida_sucesso), Toast.LENGTH_LONG).show()
            } else {
                mTaskBusiness.update(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_alterada_sucesso), Toast.LENGTH_LONG).show()
            }

            finish()

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }

    private fun openDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SIMPLE_DATE_FORMAT.format(calendar.time)
        buttonData.text = dateFormat
    }

    private fun getIndex(id: Int): Int {
        var index = 0
        for (i in 0..mLstPrioritiesEntity.size) {
            if (mLstPrioritiesEntity[i].id == id) {
                index = i
                break
            }
        }
        return index
    }

    private fun loadPriorities() {
        mLstPrioritiesEntity = mPriorityBusiness.getList()

        val lstPriorities = mLstPrioritiesEntity.map { it.description }
        mLstPrioritiesId = mLstPrioritiesEntity.map { it.id }.toMutableList()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinner.adapter = adapter
    }
}

package com.company.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.company.task.R
import com.company.task.business.PriorityBusiness
import com.company.task.constants.TaskConstants
import com.company.task.repository.PriorityCacheConstants
import com.company.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()
        startDefaultFragment()
        formatUserName()
        formatDate()

    }

    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_done -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            R.id.nav_todo -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            R.id.nav_logout -> {
                logOut()
                return false
            }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun startDefaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun logOut() {
        mSecurityPreferences.removeStroredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStroredString(TaskConstants.KEY.USER_EMAIL)
        mSecurityPreferences.removeStroredString(TaskConstants.KEY.USER_NAME)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun formatDate() {
        val c = Calendar.getInstance()

        val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-Feira", "Sexta-feira", "Sábado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")

        Calendar.DECEMBER
        //${months[c.get(Calendar.DAY_OF_WEEK)]}

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.DAY_OF_WEEK)]}"
        textDateDescription.text = str

    }

    private fun formatUserName() {
        val str = "Olá, ${mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)}!"
        textHello.text = str

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)
        name.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        email.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
    }
}

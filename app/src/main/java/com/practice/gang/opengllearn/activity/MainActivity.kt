package com.practice.gang.opengllearn.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.practice.gang.opengllearn.R
import com.practice.gang.opengllearn.fragment.BaseFragment
import com.practice.gang.opengllearn.fragment.ItemFactory

class MainActivity : AppCompatActivity(), BaseFragment.OnListFragmentInteractionListener {

    private val TAG: String = "MainActivity"
    private lateinit var mToolbar: Toolbar
    private var map: Map<String, List<Class<*>>> = HashMap()

    override fun onListFragmentInteraction(item: ItemFactory.FragmentItem) {
        startActivity(Intent(this, item.getActivityClass()))
    }

    override fun getActivityClasses(pkg: String): List<Class<*>> {
        var classes = map.get(pkg)
        if (null == classes) {
            return listOf()
        }
        return classes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,
                drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        var navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            onNavigationItemSelected(item)
        })

        val intent: Intent = Intent(Intent.ACTION_MAIN, null)
        intent.setPackage(packageName)

        var infos = packageManager.queryIntentActivities(intent, 0)
        for (info in infos) {
            val name: String = info.activityInfo.name
            val pkg: String = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1, name.lastIndexOf("."))
            Log.e("gangchen123", "MainActivity--onCreate--name: " + name + " ,pkg: " + pkg)
            try {
                if (map.containsKey(pkg)) {
                    val one: List<Class<*>>? = map.get(pkg)
                    if (one == null) {
                        val items: MutableList<Class<*>> = mutableListOf()
                        items.add(Class.forName(name))
                        var tempone = map.toMutableMap()
                        tempone.put(pkg, items)
                        map = tempone
                    } else {
                        var temp: MutableList<Class<*>> = one.toMutableList()
                        temp.add(Class.forName(name))
                        var tempone = map.toMutableMap()
                        tempone.put(pkg, temp)
                        map = tempone
                    }
                } else {
                    val item = listOf(Class.forName(name))
                    Log.e("gangchen123", "MainActivity--Class.forName(name): " + Class.forName(name))
                    var temp = map.toMutableMap()
                    temp.put(pkg, item)
                    map = temp
                }
            } catch (ex: ClassNotFoundException) {
                ex.printStackTrace()
            }

        }
        navFragment("gettingstart")
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val title: String = item.title.toString()
        navFragment(title)

        val drawer: DrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun navFragment(title: String): Unit {
        this.supportFragmentManager.beginTransaction().replace(R.id.content_main, BaseFragment.newInstance(title)).commit()
        mToolbar.setTitle(title.toUpperCase())
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

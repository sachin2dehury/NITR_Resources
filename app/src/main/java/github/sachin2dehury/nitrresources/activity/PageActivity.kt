package github.sachin2dehury.nitrresources.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.adapter.ListAdapter
import github.sachin2dehury.nitrresources.adapter.PageAdapter
import github.sachin2dehury.nitrresources.core.*
import kotlinx.android.synthetic.main.activity_page.*
import kotlinx.android.synthetic.main.page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

        jobs().invokeOnCompletion {
            jobValidator(it)
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewPager.adapter = PageAdapter()
        TabLayoutMediator(tabView, viewPager) { tab, position ->
            tab.text = pages[position]
        }.attach()

        navigationDrawer.setNavigationItemSelectedListener { item ->
            Core.navDrawerMenu(item, this)
            true
        }

        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = ALL
            }
            startActivityForResult(intent, REQUEST_CODE_OPEN_FILE)
        }

        tabView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Core.updateDocList(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Core.updateDocList(tab!!.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Core.updateDocList(tab!!.position)
            }
        })

    }

    private fun jobValidator(throwable: Throwable?) = CoroutineScope(Dispatchers.Main).launch {
        progressBar.visibility = View.GONE
        if (throwable != null) {
            errorText.visibility = View.VISIBLE
            errorText.text = throwable.toString()
            Toast.makeText(this@PageActivity, throwable.toString(), Toast.LENGTH_SHORT).show()
            Log.w("jobValidator", "Failed")
        }
    }

    private fun jobs() = CoroutineScope(Dispatchers.IO).launch {
        Core.getList(NOTES_LIST)
        Core.getList(ASSIGNMENT_LIST)
        Core.getList(SLIDES_LIST)
        Core.getList(LAB_LIST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_OPEN_FILE && resultCode == RESULT_OK) {
            Core.changeActivity(this, false, data!!.data.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu!!.findItem(R.id.searchBar).actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = listView.adapter as ListAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        Core.optionMenu(item)
        return true
    }
}
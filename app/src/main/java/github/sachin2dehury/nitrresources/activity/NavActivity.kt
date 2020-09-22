package github.sachin2dehury.nitrresources.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.STREAM_LIST
import github.sachin2dehury.nitrresources.fragment.ListFragment
import github.sachin2dehury.nitrresources.fragment.LoginFragment
import github.sachin2dehury.nitrresources.fragment.RenameFragment
import kotlinx.android.synthetic.main.activity_nav.*

open class NavActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onStop() {
        super.onStop()
        Core.saveAppData(this)
    }

    override fun onStart() {
        super.onStart()
        Core.loadAppData(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        val login = intent.getBooleanExtra("Login", true)
        if (login) {
            if (Core.firebaseAuth.currentUser == null) {
                Core.changeFragment(LoginFragment(), supportFragmentManager, false)
            } else {
                Core.changeFragment(ListFragment(STREAM_LIST), supportFragmentManager, false)
            }
        } else {
            val file = intent.getStringExtra("File")!!
            val rename = intent.getBooleanExtra("Rename", false)
            val index = intent.getIntExtra("PageIndex", 0)
            Core.changeFragment(RenameFragment(file, rename, index), supportFragmentManager)
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        navigationDrawer.setNavigationItemSelectedListener { item ->
            Core.navDrawerMenu(item, this, supportFragmentManager)
            true
        }

        MobileAds.initialize(this) {}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        Core.optionMenu(item, supportFragmentManager)
        return true
    }
}
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
import kotlinx.android.synthetic.main.activity_nav.*

open class NavActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onDestroy() {
        super.onDestroy()
        Core.saveAppData(this)
    }

    override fun onStart() {
        super.onStart()
        Core.loadAppData(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (Core.firebaseAuth.currentUser == null) {
            Core.changeFragment(LoginFragment(), supportFragmentManager)
        } else {
            Core.changeFragment(ListFragment(STREAM_LIST), supportFragmentManager)
        }

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
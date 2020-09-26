package github.sachin2dehury.nitrresources.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppMenu
import github.sachin2dehury.nitrresources.component.AppPreference
import github.sachin2dehury.nitrresources.component.AppScreen
import github.sachin2dehury.nitrresources.fragment.ListFragment
import github.sachin2dehury.nitrresources.fragment.LoginFragment
import kotlinx.android.synthetic.main.activity_nav.*

open class NavActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onStop() {
        super.onStop()
        AppPreference.saveAppData(this)
    }

    override fun onStart() {
        super.onStart()
        AppPreference.loadAppData(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        if (AppCore.firebaseAuth.currentUser == null) {
            AppScreen.changeFragment(LoginFragment(), supportFragmentManager, false)
        } else {
            AppScreen.changeFragment(
                ListFragment(AppCore.STREAM_LIST),
                supportFragmentManager,
                false
            )
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        navigationDrawer.setNavigationItemSelectedListener { item ->
            AppMenu.navDrawerMenu(item, this, supportFragmentManager)
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
        AppMenu.optionMenu(item, this, supportFragmentManager)
        return true
    }
}
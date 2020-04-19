package ar.edu.utn.frba.mobile.clases_2020c1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases_2020c1.ui.main.MainFragment
import ar.edu.utn.frba.mobile.clases_2020c1.ui.main.StatusUpdateFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener, StatusUpdateFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            // Solo la primera vez que corre el activity
            // Las demás el propio manager restaura todo como estaba
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance())
                .commit()
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.fragment_push_enter, R.anim.fragment_push_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

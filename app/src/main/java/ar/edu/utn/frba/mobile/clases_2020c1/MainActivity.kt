package ar.edu.utn.frba.mobile.clases_2020c1

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases_2020c1.fragments.EditImageFragment
import ar.edu.utn.frba.mobile.clases_2020c1.fragments.ImagesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImagesFragment.ImagesFragmentInteractionListener, EditImageFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ImagesFragment.newInstance())
                .commit()
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popFragment() {
        supportFragmentManager.popBackStack()
    }
}

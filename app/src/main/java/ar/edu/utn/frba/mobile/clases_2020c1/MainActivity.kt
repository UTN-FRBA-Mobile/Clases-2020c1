package ar.edu.utn.frba.mobile.clases_2020c1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import ar.edu.utn.frba.mobile.clases_2020c1.ui.main.MainFragment

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(getString(R.string.soy_un_textview)))
                    .commitNow()
        }
    }

    override fun onButtonTapped() {
        AlertDialog.Builder(this)
            .setTitle(R.string.hello)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .create().show()
    }
}

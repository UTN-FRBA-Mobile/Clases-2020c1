package ar.edu.utn.frba.mobile.clases_2020c1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar!!.setTitle("Conversor de Unidades de Temperatura")

        val conversor = ConversorUnidades()
        val botonC = findViewById(R.id.botonCelsius) as ImageButton

        botonC.setOnClickListener {
            // Llamo a celsiustof y celsius to k
            val valorC = temperaturaIngresadaC.text.toString().toDouble()
            temperaturaIngresadaF.setText(conversor.celsiusToF(valorC).toString())
            temperaturaIngresadaK.setText(conversor.celsiusToK(valorC).toString())
            it.hideKeyboard()
        }

        val botonK = findViewById(R.id.botonKelvin) as ImageButton

        botonK.setOnClickListener {
            // Llamo a kelvintoc y kelvintof
            val valorK = temperaturaIngresadaK.text.toString().toDouble()
            temperaturaIngresadaC.setText(conversor.kelvinToC(valorK).toString())
            temperaturaIngresadaF.setText(conversor.kelvinToF(valorK).toString())
            it.hideKeyboard()
        }

        val botonF = findViewById(R.id.botonFahrenheit) as ImageButton

        botonF.setOnClickListener {
            // Llamo a fahtoc y fahtok
            val valorF = temperaturaIngresadaF.text.toString().toDouble()
            temperaturaIngresadaC.setText(conversor.fahToC(valorF).toString())
            temperaturaIngresadaK.setText(conversor.fahToK(valorF).toString())
            it.hideKeyboard()
        }
    }
    //Esto es extensionMethods!
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

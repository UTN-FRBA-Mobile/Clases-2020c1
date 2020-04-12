package ar.edu.utn.frba.mobile.clases_2020c1.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ar.edu.utn.frba.mobile.clases_2020c1.R
import kotlinx.android.synthetic.main.fragment_sign_up_step_one.*

class SignUpStepOneFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_step_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_button.setOnClickListener {
            listener!!.onSignUpNextStep()
        }

        viewModel = ViewModelProviders.of(activity!!).get(SignUpViewModel::class.java)

        firstName.setText(viewModel.firstName)
        lastName.setText(viewModel.lastName)
        dni.setText(viewModel.dni)
        phoneNumer.setText(viewModel.phoneNumer)
    }

    override fun onPause() {
        viewModel.firstName = firstName?.text.toString()
        viewModel.lastName = lastName?.text.toString()
        viewModel.dni = dni?.text.toString()
        viewModel.phoneNumer = phoneNumer?.text.toString()

        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onSignUpNextStep()
    }
}

package ar.edu.utn.frba.mobile.clases_2020c1.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.clases_2020c1.R
import kotlinx.android.synthetic.main.fragment_sign_up_step_two.*

class SignUpStepTwoFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_step_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUp_button.setOnClickListener {
            listener!!.onFinishSignUp()
        }

        viewModel = ViewModelProvider(activity!!).get(SignUpViewModel::class.java)

        userName.setText(viewModel.userName)
        password.setText(viewModel.password)
    }

    override fun onPause() {
        viewModel.userName = userName?.text.toString()
        viewModel.password = password?.text.toString()

        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFinishSignUp()
    }
}


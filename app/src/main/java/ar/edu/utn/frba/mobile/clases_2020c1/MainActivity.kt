package ar.edu.utn.frba.mobile.clases_2020c1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ar.edu.utn.frba.mobile.clases_2020c1.ui.dashboard.DashboardFragment
import ar.edu.utn.frba.mobile.clases_2020c1.ui.login.LoginFragment
import ar.edu.utn.frba.mobile.clases_2020c1.ui.signup.SignUpStepOneFragment
import ar.edu.utn.frba.mobile.clases_2020c1.ui.signup.SignUpStepTwoFragment

class MainActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener, SignUpStepOneFragment.OnFragmentInteractionListener, SignUpStepTwoFragment.OnFragmentInteractionListener {
    private lateinit var loginFragment: Fragment
    private lateinit var signUpStepOneFragment: Fragment
    private lateinit var signUpStepTwoFragment: Fragment
    private lateinit var dashboardFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            loginFragment = LoginFragment()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, loginFragment)
                    .commitNow()
        }
    }

    override fun onLogin(username: String, password: String) {
        dashboardFragment = DashboardFragment()

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().remove(loginFragment).add(R.id.container, dashboardFragment).commitNow()
    }

    override fun onSignUp() {
        signUpStepOneFragment = SignUpStepOneFragment()

        supportFragmentManager.beginTransaction().remove(loginFragment).add(R.id.container, signUpStepOneFragment).addToBackStack(null).commit()
    }

    override fun onSignUpNextStep() {
        signUpStepTwoFragment = SignUpStepTwoFragment()

        supportFragmentManager.beginTransaction().remove(signUpStepOneFragment).add(R.id.container, signUpStepTwoFragment).addToBackStack(null).commit()
    }

    override fun onFinishSignUp() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().show(loginFragment).commitNow()
    }
}

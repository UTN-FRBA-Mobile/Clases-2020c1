package ar.edu.utn.frba.mobile.clases_2020c1.ui.status_update

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases_2020c1.R
import kotlinx.android.synthetic.main.status_update_fragment.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StatusUpdateFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StatusUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StatusUpdateFragment : Fragment(), ColorsAdapter.ItemClickListener {

    private var listener: OnFragmentInteractionListener? = null
    val colorsAdapter = ColorsAdapter(this)
    val backgroundDrawable = GradientDrawable().apply {
        orientation = GradientDrawable.Orientation.TL_BR
        setColor(Color.WHITE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.status_update_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.colorsRecycler).apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = colorsAdapter
        }
        textField.background = backgroundDrawable
        textField.setTextColor(Color.WHITE)
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

    override fun onItemClick(position: Int) {
        val colors = colorsAdapter.getColors(position)
        backgroundDrawable.colors = colors
        val backColor = colors.get(0)
        val isLight = ((backColor shr 16 and 0xFF) + (backColor shr 8 and 0xFF) + (backColor and 0xFF)) > 382
        textField.setTextColor(if (isLight) Color.BLACK else Color.WHITE)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StatusUpdate.
         */
        @JvmStatic
        fun newInstance() =
            StatusUpdateFragment()
    }
}

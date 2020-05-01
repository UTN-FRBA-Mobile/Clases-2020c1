package ar.edu.utn.frba.mobile.clases_2020c1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import ar.edu.utn.frba.mobile.clases_2020c1.R
import kotlinx.android.synthetic.main.fragment_edit_image_details.view.*

class EditImageDetailsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private var listener: EditImageDetailsFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_image_details, container, false)

        // keeping brightness value b/w -100 / +100
        view.seekbar_brightness.max = 200
        view.seekbar_brightness.progress = 100

        // keeping contrast value b/w 1.0 - 3.0
        view.seekbar_contrast.max = 20
        view.seekbar_contrast.progress = 0

        // keeping saturation value b/w 0.0 - 3.0
        view.seekbar_saturation.max = 30
        view.seekbar_saturation.progress = 10

        view.seekbar_brightness.setOnSeekBarChangeListener(this)
        view.seekbar_contrast.setOnSeekBarChangeListener(this)
        view.seekbar_saturation.setOnSeekBarChangeListener(this)

        return view
    }

    override fun onProgressChanged(seekBar: SeekBar, initialProgress: Int, b: Boolean) {
        var progress = initialProgress
        if (listener != null) {

            if (seekBar.id == R.id.seekbar_brightness) {
                // brightness values are b/w -100 to +100
                listener!!.onBrightnessChanged(progress - 100)
            }

            if (seekBar.id == R.id.seekbar_contrast) {
                // converting int value to float
                // contrast values are b/w 1.0f - 3.0f
                // progress = progress > 10 ? progress : 10;
                progress += 10
                val floatVal = .10f * progress
                listener!!.onContrastChanged(floatVal)
            }

            if (seekBar.id == R.id.seekbar_saturation) {
                // converting int value to float
                // saturation values are b/w 0.0f - 3.0f
                val floatVal = .10f * progress
                listener!!.onSaturationChanged(floatVal)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        if (listener != null)
            listener!!.onEditStarted()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (listener != null)
            listener!!.onEditCompleted()
    }

    fun setListener(listenerDetails: EditImageDetailsFragmentListener) {
        this.listener = listenerDetails
    }

    fun resetControls() {
        view!!.seekbar_brightness.progress = 100
        view!!.seekbar_contrast.progress = 0
        view!!.seekbar_saturation.progress = 10
    }

    interface EditImageDetailsFragmentListener {
        fun onBrightnessChanged(brightness: Int)

        fun onSaturationChanged(saturation: Float)

        fun onContrastChanged(contrast: Float)

        fun onEditStarted()

        fun onEditCompleted()
    }
}
package ar.edu.utn.frba.mobile.clases_2020c1.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import ar.edu.utn.frba.mobile.clases_2020c1.R
import ar.edu.utn.frba.mobile.clases_2020c1.adapters.ViewPagerAdapter
import ar.edu.utn.frba.mobile.clases_2020c1.utils.storage.fileSystem.ExternalContent
import ar.edu.utn.frba.mobile.clases_2020c1.utils.storage.fileSystem.InternalStorage
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter
import kotlinx.android.synthetic.main.fragment_edit_image.*
import java.util.*

const val IMAGE_PATH = "IMAGE_PATH"

class EditImageFragment : Fragment(), FiltersListFragment.FiltersListFragmentListener,
    EditImageDetailsFragment.EditImageDetailsFragmentListener {

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    private var listener: OnFragmentInteractionListener? = null
    private var brightnessFinal = 0
    private var saturationFinal = 1.0f
    private var contrastFinal = 1.0f

    private lateinit var imageUri: Uri
    private lateinit var originalImage: Bitmap
    private var originalFilter = Filter()
    private lateinit var prefilteredImage: Bitmap
    private lateinit var filteredImage: Bitmap
    private lateinit var filtersListFragment: FiltersListFragment
    private lateinit var editImageDetailsFragment: EditImageDetailsFragment
    private lateinit var processHandler: android.os.Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            imageUri = Uri.parse(it.getString(IMAGE_PATH))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_image, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        originalImage = ExternalContent.getBitmapFromGallery(context!!, imageUri, 100, 100)
        filteredImage = originalImage

        image_to_edit.setImageBitmap(originalImage)

        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
        Thread({
            Looper.prepare()
            processHandler = Handler(Looper.myLooper())
            Looper.loop()
        }).start()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        processHandler.post {
            Looper.myLooper()!!.quit()
        }
    }

    override fun onFilterSelected(filter: Filter) {
        resetControls()
        originalFilter = filter
        updateFilter()
    }

    fun updateFilter() {
        processHandler.removeCallbacksAndMessages(null)
        processHandler.post {
            prefilteredImage = originalFilter.processFilter(originalImage.copy(Bitmap.Config.ARGB_8888, true))
            filteredImage = generatedFilter.processFilter(prefilteredImage.copy(Bitmap.Config.ARGB_8888, true))
            activity?.runOnUiThread { image_to_edit.setImageBitmap(filteredImage) }
        }
    }
    fun quickFilter() {
        processHandler.removeCallbacksAndMessages(null)
        processHandler.post {
            val newImage = generatedFilter.processFilter(prefilteredImage.copy(Bitmap.Config.ARGB_8888, true))
            activity?.runOnUiThread { image_to_edit.setImageBitmap(newImage) }
        }
    }

    override fun onContrastChanged(contrast: Float) {
        contrastFinal = contrast
        quickFilter()
    }

    override fun onEditStarted() {
    }

    val generatedFilter
        get() = Filter().apply {
            addSubFilter(BrightnessSubFilter(brightnessFinal))
            addSubFilter(ContrastSubFilter(contrastFinal))
            addSubFilter(SaturationSubfilter(saturationFinal))
        }

    override fun onEditCompleted() {
        updateFilter()
    }

    override fun onSaturationChanged(saturation: Float) {
        saturationFinal = saturation
        quickFilter()
    }

    override fun onBrightnessChanged(brightness: Int) {
        brightnessFinal = brightness
        quickFilter()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)

        filtersListFragment = FiltersListFragment.newInstance(imageUri)
        filtersListFragment.setListener(this)

        editImageDetailsFragment = EditImageDetailsFragment()
        editImageDetailsFragment.setListener(this)

        adapter.addFragment(filtersListFragment, "Filtros")
        adapter.addFragment(editImageDetailsFragment, "Calibración")

        viewPager.adapter = adapter
    }

    private fun resetControls() {
        if (::editImageDetailsFragment.isInitialized) {
            editImageDetailsFragment.resetControls()
        }
        brightnessFinal = 0
        saturationFinal = 1.0f
        contrastFinal = 1.0f
    }

    fun save() {
        InternalStorage.saveFile(context!!, filteredImage, Calendar.getInstance().time.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel -> {
                listener!!.popFragment()
                return true
            }
            R.id.action_save -> {
                save()
                listener!!.popFragment()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    interface OnFragmentInteractionListener {
        fun popFragment()
    }

    companion object {
        @JvmStatic
        fun newInstance(imageUri: Uri) =
            EditImageFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_PATH, imageUri.toString())
                }
            }
    }
}

package com.fyp.sahayogapp.dashboard.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.utils.Conts
import com.fyp.sahayogapp.utils.Conts.MY_LOCATION_LAT_LONG

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var mapView : WebView
/**
 * A simple [Fragment] subclass.
 * Use the [MapViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapViewFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var myLocation: String? = null
    private var destination: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myLocation = it.getString(MY_LOCATION_LAT_LONG)
            destination = it.getString(Conts.DESTINATION_LAT_LONG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map_view, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        Toast.makeText(context, myLocation+destination, Toast.LENGTH_SHORT).show()
       mapView.settings.javaScriptEnabled = true
        mapView.webViewClient = WebViewClient()
       mapView.loadUrl("https://www.google.com/maps/dir/?api=1&origin=${myLocation}&destination=${destination}")

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.fyp.sahayogapp.donation.frags

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.dashboard.model.VenueData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.ClassCastException
import java.util.*
import com.fyp.sahayogapp.donation.frags.AddVenueDialogFragment.OnInputListener


private const val LOCATION_REQUEST = 101

class AddVenueDialogFragment : DialogFragment() {
    private lateinit var venueName:EditText
    private lateinit var addressET : EditText
    private lateinit var phoneNumber:EditText
    private lateinit var startTime:EditText
    private lateinit var endTime:EditText
    private lateinit var done:Button
    private lateinit var cancel:ImageView
    private lateinit var getLocation:TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude = ""
    var longitude = ""


    interface OnInputListener {
        fun sendInput(venueInfo: VenueData?)
    }

    var mOnInputListener: OnInputListener? = null


    fun setListener(mOnInputListener: OnInputListener){
        this.mOnInputListener = mOnInputListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_add_venue, container, false)
        return rootView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        done = view.findViewById(R.id.doneBtn)
        cancel = view.findViewById(R.id.cancelBtn)
        getLocation = view.findViewById(R.id.getLocation)
        addressET = view.findViewById(R.id.location)
        venueName = view.findViewById(R.id.hospital_name)
        phoneNumber = view.findViewById(R.id.phoneNumber)
        startTime = view.findViewById(R.id.wtStart)
        endTime = view.findViewById(R.id.wtEnd)
        fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(requireActivity())
        cancel.setOnClickListener {
            dialog?.dismiss()
        }
        getLocation.setOnClickListener{
            requestLocationPermission()
        }
        done.setOnClickListener {


            var venue = VenueData(
                "",
                venueName.text.toString(),
                phoneNumber.text.toString(),
                "",
                startTime.text.toString(),
                endTime.text.toString(),
                latitude,
                longitude
            )

            mOnInputListener?.sendInput(venue)
            dialog?.dismiss()
        }



    }
    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {

            fetchLocation()
        } else {


            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST)

        }


    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    latitude = it.latitude.toString()
                    longitude = it.longitude.toString()
                    Log.d("LOC", "${it.latitude},${it.longitude}")
                    val address = getAddress(it.latitude, it.longitude)
                    addressET.setText(address)
//                    Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

    }
    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lat,
            lng,
            1)
        if (addresses.size == 0) {
            return "Error"
        } else {
            return addresses[0].getAddressLine(0)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(requireActivity()!!,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_REQUEST)
                } else {

                    AlertDialog.Builder(requireContext())
                        .setTitle("Permission Required")
                        .setMessage( "This app requires access to Location Service to proceed. Would you like to open setting and grant permission?", )
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("Open Settings",                         DialogInterface.OnClickListener { dialog, which ->
                            startActivityForResult(Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts(
                                    "package",
                                    requireActivity().packageName,
                                    null
                                )
                            }, LOCATION_REQUEST)
                        })
                        .show()

                }

            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
//            mOnInputListener = targetFragment as OnInputListener?
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: ClassCastException: "
                    + e.message)
        }
    }

}
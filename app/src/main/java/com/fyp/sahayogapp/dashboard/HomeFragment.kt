package com.fyp.sahayogapp.dashboard


import android.Manifest
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity
import com.fyp.sahayogapp.base.BaseFragment
import com.fyp.sahayogapp.custom.CustomTextView
import com.fyp.sahayogapp.dashboard.adapters.RequestListAdapter
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.utils.Conts.DONATION_DATA
import com.fyp.sahayogapp.utils.PreferenceHelper.getAccessToken
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

private const val LOCATION_REQUEST = 101
private const val TAG = "HomeFragment"

class HomeFragment : BaseFragment() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var userListRecyclerView: RecyclerView
    lateinit var requestListAdapter: RequestListAdapter
    lateinit var requestViewModel: RequestViewModel
    lateinit var refresh: SwipeRefreshLayout
    lateinit var title: CustomTextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showProgress()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
//        locationBtn = view.findViewById<Button>(R.id.locationBtn)
//        locationTv = view.findViewById(R.id.locationTV)
        userListRecyclerView = view.findViewById(R.id.userListRecycler)
        refresh = view.findViewById(R.id.refresh)
        requestListAdapter = RequestListAdapter()
        title = view.findViewById(R.id.title)
//        title.setOnClickListener {
//
//            val locationPickerIntent = LocationPickerActivity.Builder()
//                .withLocation(41.4036299, 2.1743558)
//                .withGeolocApiKey("AIzaSyBBCT3i0EcpvsDC3yQwjslbSNZRkt4YxyQ")
////                .withGooglePlacesApiKey("AIzaSyC9bqxXccDPzltY1mKVygRWLyYQMKZV8-U")
//                .withSearchZone("es_ES")
//                .withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366),
//                    LatLng(43.906271, 5.394197)))
//                .withDefaultLocaleSearchZone()
//                .shouldReturnOkOnBackPressed()
//                .withStreetHidden()
//                .withCityHidden()
//                .withZipCodeHidden()
//                .withSatelliteViewHidden()
//                .withGoogleTimeZoneEnabled()
//                .withVoiceSearchHidden()
//                .withUnnamedRoadHidden()
//                .build(requireContext())
//
//            startActivityForResult(locationPickerIntent, 122)
//        }
        title.setOnClickListener {
            requestLocationPermission()
        }
        requestListAdapter.setOnDonateClick(object : RequestListAdapter.onDonateClick {
            override fun goToDetails(position: Int) {
                var bundle = Bundle()
                bundle.putSerializable(DONATION_DATA, requestListAdapter.userList[position])
                Navigation.findNavController(userListRecyclerView)
                    .navigate(R.id.action_nav_home_to_donationDetailFragment, bundle)

            }


        })
//        locationBtn.setOnClickListener {
//            fetchLocation()
//        }
        refresh.setOnRefreshListener {
            initViewModel()

        }
        var token = getAccessToken()
        var userID = getUserId()
        var userRole = getUserRole()
//        locationTv.text= "User ID: $userID , User Role: $userRole"

        initViewModel()
        initUserListRecycler()
    }

    fun initUserListRecycler() {
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestListAdapter
        }
    }

    fun initViewModel() {
//    showProgress()
        requestViewModel = ViewModelProvider(this).get(RequestViewModel::class.java)


        requestViewModel.getDonationListObserver()
            .observe(requireActivity(), Observer<List<DonationRequestModel>> {
                if (it == null) {
//                dismissProgress()
//                showAlert("Sorry!","No results Found!")
                } else {

                    requestListAdapter.userList = it.toMutableList()
//                dismissProgress()
                    refresh.isRefreshing = false
                    requestListAdapter.notifyDataSetChanged()

                }
            })
        requestViewModel.getDonationList()
    }


    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
//            Toast.makeText(requireContext(), "permission granted", Toast.LENGTH_SHORT).show()

            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    Log.d("LOC", "${it.latitude},${it.longitude}")
                    val address = getAddress(it.latitude, it.longitude)
                    Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show()
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
                        AuthActivity.MY_PERMISSIONS_REQUEST_LOCATION)
                } else {
                    showAlert("Permission Required",
                        "This app requires access to Location Service to proceed. Would you like to open setting and grant permission?",
                        "Open Settings",
                        DialogInterface.OnClickListener { dialog, which ->
                            startActivityForResult(Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts(
                                    "package",
                                    requireActivity().packageName,
                                    null
                                )
                            }, LOCATION_REQUEST)
                        })

                }

            }
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

}
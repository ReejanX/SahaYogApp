package com.fyp.sahayogapp.dashboard

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import com.fyp.sahayogapp.custom.CustomTextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.fyp.sahayogapp.dashboard.adapters.RequestListAdapter
import com.fyp.sahayogapp.dashboard.model.DonationRequestModel
import com.fyp.sahayogapp.dashboard.viewModel.RequestViewModel
import com.fyp.sahayogapp.utils.Conts.DONATION_DATA
import com.fyp.sahayogapp.utils.PreferenceHelper.getAccessToken
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserId
import com.fyp.sahayogapp.utils.PreferenceHelper.getUserRole
import androidx.navigation.NavController



import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fyp.sahayogapp.R


class HomeFragment : Fragment() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var userListRecyclerView: RecyclerView
    lateinit var  requestListAdapter: RequestListAdapter
    lateinit var requestViewModel :RequestViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        locationBtn = view.findViewById<Button>(R.id.locationBtn)
//        locationTv = view.findViewById(R.id.locationTV)
        userListRecyclerView = view.findViewById(R.id.userListRecycler)
        requestListAdapter = RequestListAdapter()


        requestListAdapter.setOnDonateClick(object :RequestListAdapter.onDonateClick{
            override fun goToDetails(position: Int) {
                Toast.makeText(requireContext(), ""+position, Toast.LENGTH_SHORT).show()
                var bundle = Bundle()
                bundle.putSerializable(DONATION_DATA,requestListAdapter.userList[position])
                Navigation.findNavController(userListRecyclerView).navigate(R.id.action_nav_home_to_donationDetailFragment,bundle)

            }


        })
//        locationBtn.setOnClickListener {
//            fetchLocation()
//        }

        var token = getAccessToken()
        var userID = getUserId()
        var userRole = getUserRole()
//        locationTv.text= "User ID: $userID , User Role: $userRole"

        initViewModel()
        initUserListRecycler()
//        initViewModel()
    }
    fun initUserListRecycler (){
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestListAdapter
        }
    }

    fun initViewModel(){

       requestViewModel= ViewModelProvider(this).get(RequestViewModel::class.java)


        requestViewModel.getDonationListObserver().observe(requireActivity(), Observer<List<DonationRequestModel>> {
            if (it==null){

                Toast.makeText(requireContext(), "No results Found", Toast.LENGTH_SHORT).show()
            }else{

                requestListAdapter.userList = it.toMutableList()
                requestListAdapter.notifyDataSetChanged()

            }
        })
        requestViewModel.getDonationList()
    }


//    private fun fetchLocation(){
//       if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//           Toast.makeText(requireContext(), "permission granted", Toast.LENGTH_SHORT).show()
//
//           val task = fusedLocationProviderClient.lastLocation
//           task.addOnSuccessListener {
//               if(it!=null){
//                   Toast.makeText(requireContext(),"${it.latitude},${it.longitude}",Toast.LENGTH_SHORT).show()
//                   Log.d("LOC","${it.latitude},${it.longitude}")
//                   locationTv.text= "${it.latitude},${it.longitude}"
//                   val address = getAddress(it.latitude,it.longitude)
//                   locationTv.text = "Address: $address"
//               }
//           }
//        } else {
//           Toast.makeText(requireContext(), "permission notgranted", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//    }
     private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }


}
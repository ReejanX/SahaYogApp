package com.fyp.sahayogapp.donation.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var bloodRadio: RadioButton
    private lateinit var plateletRadio: RadioButton
    private lateinit var backBtn: ImageButton
    private lateinit var bloodGroup: AutoCompleteTextView
    private lateinit var reason: AutoCompleteTextView
    private lateinit var pints: AutoCompleteTextView

    private lateinit var continueBtn: Button
    private var requestType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        val bloods = resources.getStringArray(R.array.blood_group)
        val bloodGroupAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, bloods)
        bloodGroup.setAdapter(bloodGroupAdapter)

        val reasons = resources.getStringArray(R.array.reasons)
        val reasonAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, reasons)
        bloodGroup.setAdapter(reasonAdapter)

        val units = resources.getStringArray(R.array.units)
        val unitsAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, units)
        bloodGroup.setAdapter(unitsAdapter)


        backBtn.setOnClickListener {
            activity?.finish()
        }
        continueBtn.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_blood -> {
                    requestType = bloodRadio.text.toString()
                }
                R.id.radio_platelets -> {
                    requestType = plateletRadio.text.toString()
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Please select the request type",
                        Toast.LENGTH_SHORT
                    ).show()
                   return@setOnClickListener
               }
           }
        }

    }

    private fun initView(view: View) {
        radioGroup = view.findViewById(R.id.typeRadio)
        bloodRadio = view.findViewById(R.id.radio_blood)
        plateletRadio = view.findViewById(R.id.radio_platelets)
        continueBtn = view.findViewById(R.id.continueBtn)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        reason = view.findViewById(R.id.reasonDD)
        pints = view.findViewById(R.id.units)
        backBtn = view.findViewById(R.id.backBtn)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
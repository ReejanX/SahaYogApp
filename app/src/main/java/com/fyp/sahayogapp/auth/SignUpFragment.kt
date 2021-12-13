package com.fyp.sahayogapp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fyp.sahayogapp.R
import com.fyp.sahayogapp.auth.AuthActivity.Companion.hideKeyboard
import com.fyp.sahayogapp.auth.AuthActivity.Companion.nav
import com.fyp.sahayogapp.permissions.PermissionLocation


class SignUpFragment : Fragment() {
    private lateinit var root: LinearLayout
    private lateinit var scroll: LinearLayout
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var bloodGroup: AutoCompleteTextView
    private lateinit var genderAC: AutoCompleteTextView
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var back : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        root.setOnClickListener {
            it.hideKeyboard(requireContext())
        }
        scroll.setOnClickListener {
            it.hideKeyboard(requireContext())
        }
        loginBtn.setOnClickListener {
            nav(view, R.id.action_signUpFragment_to_loginFragment)
        }
        back.setOnClickListener{
            nav(view,R.id.action_signUpFragment_to_userTypeFragment)
        }

        val bloods = resources.getStringArray(R.array.blood_group)
        val bloodGroupAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, bloods)
        bloodGroup.setAdapter(bloodGroupAdapter)

        val genders = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, genders)
        genderAC.setAdapter(genderAdapter)

    }
    //initializing the components of the view
    private fun initView(view: View) {
        root = view.findViewById(R.id.root)
        scroll = view.findViewById(R.id.scroll)
        signUpBtn = view.findViewById(R.id.signUp)
        loginBtn = view.findViewById(R.id.loginBtn)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        genderAC = view.findViewById(R.id.gender)
        name = view.findViewById(R.id.full_name)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phoneNumber)
        back = view.findViewById(R.id.backBtn)

    }

//    private fun nav(view: View, id: Int) {
//        Navigation.findNavController(view).navigate(id)
//    }

//    fun View.hideKeyboard() {
//        val inputMethodManager =
//            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
//    }

}
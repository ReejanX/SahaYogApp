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


class SignUpFragment : Fragment() {
    private lateinit var root: LinearLayout
    private lateinit var scroll: LinearLayout
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var bloodGroup: AutoCompleteTextView
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText

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
            it.hideKeyboard()
        }
        scroll.setOnClickListener {
            it.hideKeyboard()
        }
        loginBtn.setOnClickListener {
            nav(view, R.id.action_signUpFragment_to_loginFragment)
        }

        val bloods = resources.getStringArray(R.array.blood_group)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, bloods)
        bloodGroup.setAdapter(arrayAdapter)

    }

    private fun initView(view: View) {
        root = view.findViewById(R.id.root)
        scroll = view.findViewById(R.id.scroll)
        signUpBtn = view.findViewById(R.id.signUp)
        loginBtn = view.findViewById(R.id.loginBtn)
        bloodGroup = view.findViewById(R.id.bloodGroup)
        name = view.findViewById(R.id.full_name)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phoneNumber)
    }

    private fun nav(view: View, id: Int) {
        Navigation.findNavController(view).navigate(id)
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }

}
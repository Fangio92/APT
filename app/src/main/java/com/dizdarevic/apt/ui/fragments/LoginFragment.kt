package com.dizdarevic.apt.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dizdarevic.apt.AppPreferences
import com.dizdarevic.apt.R
import com.dizdarevic.apt.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    var validPass=false
    var validUser=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login_btn=binding.btLogin
        val etPassword=binding.etPassword
        val etUsername=binding.etUsername

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                validPass = ValidatePassword(s)
                enableLogin()
            }
        })

        etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                validUser = ValidateUsername(s)
                enableLogin()
            }
        })


        login_btn.setOnClickListener {
            LoginUser()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun enableLogin(){
        binding.btLogin.isEnabled = validPass && validUser
    }
    private fun ValidateUsername(s: Editable?):Boolean {
        if(s.toString().length<6)
            return false

        return true
    }

    private fun ValidatePassword(s: Editable?):Boolean {
        val pat: Pattern
        val mat: Matcher
        val password = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,}$"
        pat = Pattern.compile(password)
        mat = pat.matcher(s.toString())
        return mat.matches()
    }

    private fun LoginUser() {
        GlobalScope.launch(Dispatchers.IO) {
           AppPreferences(requireContext()).storeUser(binding.etUsername.toString(), binding.etPassword.toString())
            findNavController().navigate(R.id.action_loginFragment_to_usersFragment5)
        }
    }
}
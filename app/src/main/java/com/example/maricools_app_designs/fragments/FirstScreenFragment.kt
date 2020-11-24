package com.example.maricools_app_designs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentFirstScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FirstScreenFragment : Fragment(R.layout.fragment_first_screen) {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    val user = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstScreenBinding.bind(view)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        loginUser()
    }

    private fun loginUser() {
        val CurrentUser = user.currentUser

        val action = FirstScreenFragmentDirections.actionFirstScreenFragmentToMainScreenFragment()
        if (CurrentUser == null){
            user.signInAnonymously().addOnCompleteListener{
                if (it.isSuccessful){
                       binding.loading.text = "Loading Complete"
                        findNavController().navigate(action)
                }else{
                   val mySnackbar = Snackbar.make(binding.root, "Error Loading...", Snackbar.LENGTH_LONG).setAction("Retry") {loginUser()}
                   mySnackbar.show()
                }
            }
        }else{
            findNavController().navigate(action)
        }
    }
}
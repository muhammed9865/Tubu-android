package com.example.tubu.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.tubu.R
import com.example.tubu.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.math.log

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    // Intent start
    private val registerAuth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                handleSignInResult(task)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener {
            signIn()
        }
        

        return binding.root
    }

    private fun signIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.api_key))
            .requestId()
            .build()

        val mClient = GoogleSignIn.getClient(requireContext(), options)

        mClient.signInIntent.also {
            registerAuth.launch(it)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        CoroutineScope(Dispatchers.IO).launch {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d(TAG, "handleSignInResult: ${account.idToken}")
            // Send the token to server
            // Navigate to PlayListFragment
            withContext(Dispatchers.Main) {
            binding.loginBtn.setOnClickListener {
                findNavController().navigate(R.id.playlistFragment)
            }
            }
        }catch (e: ApiException) {
            Log.d(TAG, "signInResult:failed code: ${e.statusCode} ${e.localizedMessage}")
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Failed to login", Toast.LENGTH_LONG)
                    .show()
            }
        }
        }



    }

    companion object {
        private const val TAG = "LoginFragment"
    }



}
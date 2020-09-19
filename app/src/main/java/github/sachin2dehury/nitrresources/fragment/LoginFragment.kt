package github.sachin2dehury.nitrresources.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.core.Core
import github.sachin2dehury.nitrresources.core.REQUEST_CODE_SIGN_IN
import github.sachin2dehury.nitrresources.core.STREAM_LIST
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var email: String
    private lateinit var password: String

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            if (isValidInput()) {
                progressBar.visibility = View.VISIBLE
                signIn()
            }
        }
        signUpButton.setOnClickListener {
            if (isValidInput()) {
                progressBar.visibility = View.VISIBLE
                signUp()
            }
        }
        googleSignInButton.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            signInClient.signInIntent.also { intent ->
                requireActivity().startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    private fun signUp() = CoroutineScope(Dispatchers.IO).apply {
        Core.firebaseAuth.createUserWithEmailAndPassword(email, password).apply {
            addOnCompleteListener {
                Core.fragmentManager.popBackStack()
                Core.changeFragment(ListFragment(STREAM_LIST))
            }
            addOnFailureListener {
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signIn() = CoroutineScope(Dispatchers.IO).apply {
        Core.firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            addOnCompleteListener {
                Core.fragmentManager.popBackStack()
                Core.changeFragment(ListFragment(STREAM_LIST))
            }
            addOnFailureListener {
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun isValidInput(): Boolean {
        email = userEmail.text.toString()
        password = userPassword.text.toString()
        return when {
            email.isEmpty() || email.length < 6 -> {
                Toast.makeText(context, "Invalid Email!", Toast.LENGTH_LONG).show()
                false
            }
            password.length < 8 -> {
                Toast.makeText(
                    context,
                    "Invalid Password!\nEnter a password of Length 8 or Higher.",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
            else -> {
                PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
                    putString("Email", email)
                    putString("Password", password)
                    apply()
                }
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            CoroutineScope(Dispatchers.IO).apply {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result!!
                account.let {
                    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                    Core.firebaseAuth.signInWithCredential(credentials).apply {
                        addOnCompleteListener {
                            Core.fragmentManager.popBackStack()
                            Core.changeFragment(ListFragment(STREAM_LIST))
                        }
                        addOnFailureListener {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
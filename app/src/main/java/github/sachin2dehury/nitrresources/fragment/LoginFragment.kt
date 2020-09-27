package github.sachin2dehury.nitrresources.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppNav
import github.sachin2dehury.nitrresources.core.UserDetails
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            if (isValidInput()) {
                signIn()
            }
        }
        signUpButton.setOnClickListener {
            if (isValidInput()) {
                signUp()
            }
        }
        googleSignInButton.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            signInClient.signInIntent.also { intent ->
                requireActivity().startActivityForResult(intent, AppCore.REQUEST_CODE_SIGN_IN)
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
                signInButton.isClickable = false
                signUpButton.isClickable = false
                progressBar.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
                        putString("Email", email)
                        putString("Password", password)
                        apply()
                    }
                    val user = UserDetails(email, password)
                    AppCore.firebaseFireStore.collection("User").add(user).await()
                }
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppCore.REQUEST_CODE_SIGN_IN) {
            CoroutineScope(Dispatchers.IO).apply {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result!!
                account.let {
                    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                    AppCore.firebaseAuth.signInWithCredential(credentials)
                    loggedIn()
                }
            }
        }
    }

    private fun signUp() = CoroutineScope(Dispatchers.IO).apply {
        AppCore.firebaseAuth.createUserWithEmailAndPassword(email, password).apply {
            addOnCompleteListener {
                loggedIn()
            }
            addOnFailureListener {
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signIn() = CoroutineScope(Dispatchers.IO).apply {
        AppCore.firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            addOnCompleteListener {
                loggedIn()
            }
            addOnFailureListener {
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loggedIn() {
        parentFragmentManager.popBackStack()
        AppNav.changeFragment(ListFragment(AppCore.STREAM_LIST), parentFragmentManager)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.removeItem(R.id.user)
        menu.removeItem(R.id.settings)
        super.onPrepareOptionsMenu(menu)
    }
}
package com.example.android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android.Resources.Api
import com.example.android.Resources.JsonMaker
import com.example.android.Resources.SharedPrefs
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    //Init view
    lateinit var submit: Button
    lateinit var api : Api
    lateinit var sharedPrefs: SharedPrefs
    //Load classes
    private val jsonMaker = JsonMaker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.api = Api(this)
        this.sharedPrefs = SharedPrefs(this)

        //Set variables
        val loginNameEt = findViewById<EditText>(R.id.login_name)
        val passwordEt = findViewById<EditText>(R.id.login_password)
        submit = findViewById(R.id.sign_in_button)

        //Submit login
        submit.setOnClickListener{
            val loginName = loginNameEt.text.toString()
            val password = passwordEt.text.toString()
            login(loginName, password)
        }
    }

    /**
     * @func login
     * @var name String
     * @var password String
     * This function makes an api request to the server and handles the server response
     * Saves the token in storage
     */
    private fun login(email: String, password: String){
        if(password.isBlank()){
            makeErrorDialog("Geen wachtwoord ingevuld!", "Voer een wachtwoord in.")
        }else if(!isValidEmail(email)){
            makeErrorDialog("Geen geldig e-mailadres ingevuld!", "Voer een e-mailadres in.")
        }else{
            this.api.login(email, password){ result, error ->
                if(result.toString() == "This user does not exist"){
                    makeErrorDialog("Inloggen mislukt!", "Deze gebruiker bestaat niet")
                }else if(result.toString() == "\"This password doest not exist by this user\"") {
                    makeErrorDialog("Inloggen mislukt!", "Verkeerd wachtwoord opgegeven")
                }else{
                    Log.d("DEBUG", result.toString())

                    val jsonResult = JSONObject(result.toString())
                    val token = jsonResult["token"].toString()
                    if(!token.isBlank()){
                        this.sharedPrefs.loginUser(token)
                        switchToHome()
                    }else{
                        makeErrorDialog("Inloggen mislukt!", "Inloggen is mislukt, neem contact op met de helpdesk.")
                    }
                }
            }
        }
    }


    /**
     * @func isValidEmail
     * Returns a true or false if the email is valid
     */
    private fun isValidEmail(target: CharSequence): Boolean {
        if(TextUtils.isEmpty(target)) {
            return false
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    /**
     * @func switchToHome
     * This function switches to home
     */
    private fun switchToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    /**
     * @func makeErrorDialog
     * @var title String
     * @var message String
     * This function makes an error dialog to display
     * errors that happen while logging in
     */
    private fun makeErrorDialog(title: String, message: String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("ok", DialogInterface.OnClickListener {
                    dialog, id ->(dialog.dismiss())
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    /**
     * OVERIDE
     * @func onBackPressed
     * This function helps the user not return to being logged in
     * if they logged out.
     */
    override fun onBackPressed(){
        finishAffinity()
    }
}
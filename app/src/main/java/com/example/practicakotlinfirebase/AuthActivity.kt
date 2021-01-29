package com.example.practicakotlinfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","integracion de firebase completada")
        analytics.logEvent("InitScreen",bundle)

        setup()
    }

    private fun setup(){
        title = "Autenticación"

        LogOutButton.setOnClickListener {
            if(etEmail.text.isNotEmpty() && etContraseñaEmail.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.text.toString(),
                    etContraseñaEmail.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?:"",ProviderTipo.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
        LogInButton.setOnClickListener {
            if(etEmail.text.isNotEmpty() && etContraseñaEmail.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(),
                    etContraseñaEmail.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?:"",ProviderTipo.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email:String, provider: ProviderTipo){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
    }
}
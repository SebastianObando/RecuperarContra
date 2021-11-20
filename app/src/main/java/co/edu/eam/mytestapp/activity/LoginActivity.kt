package co.edu.eam.mytestapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.mytestapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Clase principal de la aplicación desde donde se acceden a las demás pantallas
 * @author Carlos Andrés Flórez
 * @version 1.0
 */
class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        mAuth = FirebaseAuth.getInstance()

        if( mAuth.currentUser!=null ){
            irAListaActivity()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { loginUsuario() }
        binding.btnRegistro.setOnClickListener {
            startActivity( Intent(this, RegistroActivity::class.java) )
        }
        binding.btnRecuperar.setOnClickListener {
            startActivity( Intent(this, RecuperarContra::class.java) )
        }

    }

    private fun loginUsuario(){
        val email = binding.emailUser.text.toString()
        val pass = binding.passUser.text.toString()

        if( email.isNotEmpty() && pass.isNotEmpty() ){
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){
                if(it.isSuccessful){
                    irAListaActivity()
                }else{
                    Toast.makeText(baseContext, "Login incorrecto", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun irAListaActivity(){
        val intent = Intent(this, ListaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity( intent )
        finish()
    }

}
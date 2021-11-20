package co.edu.eam.mytestapp.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityRecuperarContraBinding
import com.google.firebase.auth.FirebaseAuth

class RecuperarContra : AppCompatActivity() {

    lateinit var binding: ActivityRecuperarContraBinding
    private lateinit var aAuth:FirebaseAuth

    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        aAuth = FirebaseAuth.getInstance()
        binding.btnEnviar.setOnClickListener {
            val email = binding.emailUser.text.toString()
            if(email.isNotEmpty()) {
                aAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Email Enviado", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
            }
        }
    }
}
package co.edu.eam.mytestapp.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.edu.eam.mytestapp.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import co.edu.eam.mytestapp.R
import com.google.firebase.auth.FirebaseUser

class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding
    lateinit var mAuth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var dialog: Dialog
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_progreso)
        dialog = builder.create()

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("usuarios")

        binding.btnRegistro.setOnClickListener { registrarUsuario() }
    }

    fun registrarUsuario(){
        val nombre = binding.nombreUser.text.toString()
        val nickname = binding.nicknameUser.text.toString()
        val email = binding.emailUser.text.toString()
        val password = binding.passUser.text.toString()

        setDialog(true)

        if( nombre.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() ){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user = mAuth.currentUser
                    if(user!=null){
                        verificarEmail(user)

                        val userDB = databaseReference.child(user.uid)
                        userDB.child("nombre").setValue(nombre)
                        userDB.child("nickname").setValue(nickname)

                        setDialog(false)

                        val intent = Intent(this, ListaActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity( intent )
                        finish()
                    }
                }
            }.addOnFailureListener(this){
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verificarEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(baseContext, "Email enviado", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(baseContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setDialog(show: Boolean) {
        if (show) dialog.show() else dialog.dismiss()
    }

}
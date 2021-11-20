package co.edu.eam.mytestapp.utils

import android.util.Log
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.model.pokemon.Pokemon
import com.google.firebase.database.*

object ManagerFirebase {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var dataRef: DatabaseReference = database.reference
    var listener:OnActualizarAdapter? = null

    fun guardarEstudiante(estudiante: Estudiante){
        dataRef.child("estudiantes").push().setValue(estudiante)
    }

    fun eliminarEstudiante(key:String){
        dataRef.child("estudiantes").child(key).removeValue()
    }

    fun actualizarEstudiante(key:String, estudiante: Estudiante){
        dataRef.child("estudiantes").child(key).setValue(estudiante)
    }

    fun guardarPokemon(pokemon: Pokemon){
        dataRef.child("pokemon").push().setValue(pokemon)
    }

    fun escucharEventosEstudiantes(){
        dataRef.child("estudiantes").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.v("ManagerFirebase", "onChildAdded()")
                val estudiante = snapshot.getValue(Estudiante::class.java)
                estudiante!!.key = snapshot.key
                if(listener!=null){
                    listener!!.agregarEstudianteAdapter(estudiante)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val estudiante = snapshot.getValue(Estudiante::class.java)
                if(listener!=null){
                    listener!!.actualizarEstudianteAdapter(estudiante!!)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val estudiante = snapshot.getValue(Estudiante::class.java)
                if(listener!=null){
                    listener!!.eliminarEstudianteAdapter(estudiante!!)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.v("ManagerFirebase", "onChildMoved()")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.v("ManagerFirebase", "onCancelled()")
            }
        })
    }

    interface OnActualizarAdapter{
        fun agregarEstudianteAdapter(estudiante: Estudiante)
        fun eliminarEstudianteAdapter(estudiante: Estudiante)
        fun actualizarEstudianteAdapter(estudiante: Estudiante)
    }

}
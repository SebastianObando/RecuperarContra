package co.edu.eam.mytestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.mytestapp.adapter.PokemonAdapter
import co.edu.eam.mytestapp.databinding.ActivityListaPokemonBinding
import co.edu.eam.mytestapp.model.pokemon.Pokemon
import co.edu.eam.mytestapp.service.pokemon.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaPokemonActivity : AppCompatActivity() {
    lateinit var binding:ActivityListaPokemonBinding
    lateinit var lista:Array<Pokemon?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lista = arrayOfNulls(151)

        val retrofit = Retrofit.Builder()
            .baseUrl(co.edu.eam.mytestapp.service.pokemon.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokemonService = retrofit.create(PokemonService::class.java)

        val adapter = PokemonAdapter(lista)
        binding.listaPoke.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listaPoke.adapter = adapter

        for( i in 1 .. 151){
            pokemonService.getPokemonByNumber(i).enqueue( object: Callback<Pokemon>{
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if(response.isSuccessful){ //200
                        response.body()!!.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${i}.png"
                        lista[i-1] = response.body()!!
                        adapter.notifyItemChanged(i-1)
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.e("POKEMON", t.message.toString() )
                }
            } )
        }

    }
}
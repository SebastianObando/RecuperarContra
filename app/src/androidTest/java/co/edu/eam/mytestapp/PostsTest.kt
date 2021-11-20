package co.edu.eam.mytestapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.eam.mytestapp.model.posts.Post
import co.edu.eam.mytestapp.service.posts.PostsService
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import strikt.api.expectThat

@RunWith(AndroidJUnit4::class)
class PostsTest {

    val api = Retrofit.Builder()
        .baseUrl(co.edu.eam.mytestapp.service.posts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(PostsService::class.java)

    @Test
    fun getPostsTest(){
        val call = api.findAll()

        expectThat(call.execute()){
            assertThat("La respuesta es OK"){
                it.code() == 200
            }
            assertThat("La cantidad de posts debe ser 100"){
                it.body()!!.size == 100
            }
        }

    }

    @Test
    fun obtenerPostResponseTest() {
        val call = api.findById(98)
        expectThat(call.execute()){
            assertThat("La respuesta es ok"){
                it.code() == 200
            }
            assertThat("El título del post es correcto"){
                it.body()!!.title == "laboriosam dolor voluptates"
            }
        }
    }

    @Test
    fun obtenerPostRequestTest() {
        val call = api.findById(98)
        expectThat(call.request()){
            assertThat("Es un método GET"){
                it.method() == "GET"
            }
            assertThat("La url es correcta"){
                it.url().encodedPath() == "/posts/98"
            }
        }
    }

    @Test
    fun guardarPostResponseTest() {
        val post = Post("Mi post", "Descripción de prueba", 1)
        val call = api.save(post)
        expectThat(call.execute()){
            assertThat("Se creó correctamente el post"){
                it.code() == 201
            }
            assertThat("La respuesta es diferente de null"){
                it.body() != null
            }
        }
    }

    @Test
    fun actualizarPostResponseTest() {
        val post = Post("Mi post 2", "Descripción de prueba", 1)
        val call = api.update(2, post)
        expectThat(call.execute()){
            assertThat("Se ejecutó satisfactoriamente"){
                it.code() == 200
            }
            assertThat("El título del post se actualizó"){
                it.body()!!.title == "Mi post 2"
            }
        }
    }



}
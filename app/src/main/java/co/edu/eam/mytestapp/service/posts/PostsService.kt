package co.edu.eam.mytestapp.service.posts

import co.edu.eam.mytestapp.model.posts.Comment
import co.edu.eam.mytestapp.model.posts.Post
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface PostsService {

    @GET( "posts/")
    fun findAll(): Call< List<Post> >

    @GET("posts/{id}")
    fun findById(@Path("id") id:Int ):Call<Post>

    @POST("posts/")
    fun save(@Body post:Post):Call<Post>

    @DELETE("posts/{id}")
    fun delete(@Path("id") id:Int):Call<Void>

    @PUT("posts/{id}")
    fun update(@Path("id") id:Int, @Body post:Post):Call<Post>

    @GET("posts/{id}/comments")
    fun findAllComments(@Path("id") id:Int):Call<List<Comment>>

}
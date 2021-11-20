package co.edu.eam.mytestapp.model.posts

class Post(var title:String, var body:String, var userId:Int) {
    var id:Int? = null

    override fun toString(): String {
        return "Post(title='$title', body='$body', userId=$userId, id=$id)"
    }

}
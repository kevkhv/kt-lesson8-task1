data class Message(
    val chatId:Int = 0,
    var messageId:Int = 0,
    val sender:User,
    val addressee:User,
    var text:String,
    var statusRead:Boolean = false
)
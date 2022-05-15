data class Chat(
    val chatId:Int = 0,
    val createdBy:User,
    val ownerBy:User,
    val message:MutableList<Message> = mutableListOf()
)
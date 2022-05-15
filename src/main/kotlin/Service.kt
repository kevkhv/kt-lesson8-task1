class Service {
    private val chats = mutableListOf<Chat>()
    private var cid = 0
    private fun findChat(id: Int): Chat {
        return chats.firstOrNull { it.chatId == id } ?: throw ChatNotFoundException("Чат не найден")
    }

    private fun findMessage(cid: Int, mid: Int): Message {
        return findChat(cid).message.firstOrNull { it.messageId == mid }
            ?: throw MessageNotFoundException("Сообщение не найдено")
    }

    private fun createChat(item: Chat): Chat {
        cid++
        chats.add(item.copy(chatId = cid))
        return chats.first{it.chatId == cid}
    }     //++

    fun deleteChat(item: Chat): Boolean {
        chats.remove(findChat(item.chatId))
        return true
    } //++

    fun getChats(): List<Chat> {
//        if (chats.size > 0) {
//            chats.forEach() { element ->
//                if (element.message.isNotEmpty()) {
//                    print("(${element.chatId})${element.createdBy.name}(${element.createdBy.userId}) -> ${element.ownerBy.name}(${element.ownerBy.userId}):   ")
//                    element.message.forEach { print("${it.text}(${it.messageId})(${it.chatId})...") }
//                    println()
//                } else println("(${element.chatId})нет сообщений")
//            }
//        } else println("Чаты отсутствуют")
        return chats
    }                      //++

    fun createMessage(item: Message) {
        chats.firstOrNull { it.chatId == item.chatId }
            ?.apply { message.add(item.copy(messageId = message.last().messageId + 1)) } ?:
            createChat(Chat(createdBy = item.sender, ownerBy = item.addressee)).apply { message.add(item.copy(chatId = chatId, messageId = 1)) }
    }    //++

    fun editMessage(item: Message) {
        findMessage(item.chatId, item.messageId).apply { text = item.text }
    }      //++

    fun deleteMessage(item: Message) {
        findChat(item.chatId).message.remove(findMessage(item.chatId, item.messageId))
        if (findChat(item.chatId).message.isEmpty()) deleteChat(findChat(item.chatId))
    }    //++

    fun getMessage(cid: Int, lastMid: Int, count: Int): List<Message> {
        val result = mutableListOf<Message>()
        findChat(cid).message.forEach {
            if (it.messageId >= lastMid && it.messageId <= lastMid + count - 1) {
                it.statusRead = true
                result.add(it)
            }
        }
        return result.ifEmpty { throw MessageNotFoundException("Сообщение с ID = $lastMid не найдено") }
    }   //++

    fun getUnreadChatsCount(userId: Int): Int {
        return chats.count { elements ->
            elements.message.any { !it.statusRead && it.addressee.userId == userId }
        }  //++
    }
}

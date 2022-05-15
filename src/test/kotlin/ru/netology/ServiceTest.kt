package ru.netology

import Chat
import Service
import ChatNotFoundException
import Message
import MessageNotFoundException
import User
import org.junit.Assert.*
import org.junit.Test


class ServiceTest {
    val service = Service()
    val user1 = User(userId = 1, name = "Иван", firstName = "Иванов")
    val user2 = User(userId = 2, name = "Петр", firstName = "Петров")
    val user3 = User(userId = 3, name = "Александр", firstName = "Александров")
    val message1 = Message(sender = user1, addressee = user2, text = "Hello")
    val message2 = Message(sender = user1, addressee = user3, text = "World")
    val message3 = Message(chatId = 1, sender = user1, addressee = user3, text = "Oops")


    @Test
    fun deleteChats_returnTrue() {
        //arrange
        service.createMessage(message1)
        //assert
        val actual = service.deleteChat(Chat(chatId = 1, createdBy = user1, ownerBy = user2))
        assertTrue(actual)
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteChats_returnChatNotFoundException() {
        //arrange
        service.createMessage(message1)
        //assert
        service.deleteChat(Chat(chatId = 2, createdBy = user1, ownerBy = user2))

    }

    @Test
    fun createMessage_shouldCreate() {

    }

    @Test(expected = ChatNotFoundException::class)
    fun getMessage_returnChatNotFoundException() {
        //arrange
        service.createMessage(message1)
        service.createMessage(message2)
        //assert
        val actual = service.getMessage(5, 5, 5)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getMessage_returnMessageNotFoundException() {
        //arrange
        service.createMessage(message1)
        service.createMessage(message2)
        //assert
        val actual = service.getMessage(1, 5, 5)
    }

    @Test
    fun getMessage_returnList() {
        //arrange
        service.createMessage(message1)
        service.createMessage(message2)
        val expected = listOf(
            Message(
                chatId = 1,
                messageId = 1,
                sender = user1,
                addressee = user2,
                text = "Hello",
                statusRead = true
            )
        )
        //assert
        val actual = service.getMessage(1, 1, 1)
        assertEquals(expected, actual)
    }

    @Test(expected = ChatNotFoundException::class)
    fun deleteMessage_returnChatNotFoundException() {
        //arrange
        service.createMessage(message1)
        //assert
        service.deleteMessage(message1)
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteMessage_returnMessageNotFoundException() {
        //arrange
        service.createMessage(message1)
        //assert
        service.deleteMessage(message3)
    }
}

package com.knight.repository

import com.knight.exceptions.{APIError, DuplicateRequest}
import com.knight.logic.WordFunctions
import com.knight.models.{Message, RawMessage}
import com.knight.models.tiny.{Count, Id}

import scala.concurrent.Future

class MessageRepository(var messagesDB: List[Message] = List()) {
  import com.knight.repository.RepositoryContext._

  val add: Message => Unit = message => {
    messagesDB = message :: messagesDB
  }

  private def messageCounter: Count = WordFunctions.countNumberOfWordsInMessages(messagesDB)

  def duplicateRequestValidation: RawMessage => Future[Option[APIError]] =
    message =>
      Future {
        if (WordFunctions.findMessageById(messagesDB)(Id(message.id)).isDefined) Some(DuplicateRequest) else None
    }

  def addMessage: RawMessage => Future[Count] =
    rawMessage => {
      Future {
        add(WordFunctions.toMessage(rawMessage))
        messageCounter
      }
    }

  def getMessageCount: Future[Count] = Future {
    messageCounter
  }
}

package com.knight.library

import com.knight.models.tiny.{Count, Id, Word}
import com.knight.models.{Message, RawMessage}

object WordFunctions {

  def nonBlankWords: Word => Boolean = w => w.value.nonEmpty

  def messagesToWordsConversion: List[Message] => List[Word] = messages => messages.map(m => m.words).flatten

  def countNumberOfWords: List[Word] => Count = words => Count(words.filter(nonBlankWords).size)

  def findMessageById: List[Message] => Id => Option[Message] = messages => id => messages.find(m => m.id.value.equals(id.value))

  def convertStringIntoWords: String => List[Word] = string => string.split(" ").map(s => Word(s)).filter(nonBlankWords).toList

  def countNumberOfWordsInMessages: List[Message] => Count =
    messages => messagesToWordsConversion.andThen(countNumberOfWords)(messages)

  def toMessage: RawMessage => Message = raw => Message(Id(raw.id), convertStringIntoWords(raw.message))
}

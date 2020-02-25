package com.knight.tests

import com.github.javafaker.Faker
import com.knight.models.tiny.{Id, Word}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import com.knight.logic.WordFunctions
import com.knight.models.Message

import scala.jdk.CollectionConverters._

class WordFunctionSpec extends AnyFunSuite with Matchers {

  lazy val faker       = new Faker()
  def randomWord       = faker.lorem().word()
  lazy val randomWords = faker.lorem().words(faker.random().nextInt(2, 10)).asScala.map(w => Word(w)).toList

  test("convertStringIntoWords should return a word list") {
    val string = "one"
    assert(WordFunctions.convertStringIntoWords(string).nonEmpty)
  }
  test("convertStringIntoWords should return a empty word list") {
    val string = ""
    assert(WordFunctions.convertStringIntoWords(string).isEmpty)
  }
  test("convertStringIntoWords should return a 2 words") {
    val string = "one two"
    assert(WordFunctions.convertStringIntoWords(string).size == 2)
  }
  test("convertStringIntoWords should return a 1 word with extra spaces before and after") {
    val string = "  one   "
    assert(WordFunctions.convertStringIntoWords(string).size == 1)
  }
  test("convertStringIntoWords should return a 3 words with extra spaces before, between and after words") {
    val string = "  one   two    three "
    assert(WordFunctions.convertStringIntoWords(string).size == 3)
  }

  test("findMessageById should find an empty Option") {
    val messages = List(
      Message(Id("001"), List(Word("one"), Word("two"), Word("three"), Word("six"))),
      Message(Id("003"), List(Word("one"), Word("five"), Word("three"), Word("four"))),
      Message(Id("005"), List(Word("one"), Word("seven"), Word("three"), Word("six")))
    )
    assert(WordFunctions.findMessageById(messages)(Id("0")).isEmpty)
  }
  test("findMessageById should find message with id") {
    val messages = List(
      Message(Id("001"), List(Word("one"), Word("two"), Word("three"), Word("six"))),
      Message(Id("003"), List(Word("one"), Word("five"), Word("three"), Word("four"))),
      Message(Id("005"), List(Word("one"), Word("seven"), Word("three"), Word("six")))
    )
    assert(WordFunctions.findMessageById(messages)(Id("003")).isDefined)
  }
  test("nonBlankWords should return false with a blank word") {
    val word = Word("")
    assert(WordFunctions.nonBlankWords(word) == false)
  }
  test("nonBlankWords should return true with valid word") {
    val word = Word(randomWord)
    assert(WordFunctions.nonBlankWords(word) == true)
  }
  test("messagesToWordsConversion should return a empty word list") {
    val messages = List()
    assert(WordFunctions.messagesToWordsConversion(messages).isEmpty)
  }
  test("messagesToWordsConversion should return a word list") {
    val messages = List(Message(Id("1"), List(Word("one"))))
    assert(WordFunctions.messagesToWordsConversion(messages).nonEmpty)
  }
  test("countNumberOfUniqueWords should return 0 with a empty word list") {
    val words = List()
    assert(WordFunctions.countNumberOfWords(words).count == 0)
  }
  test("countNumberOfUniqueWords should return 0 with a blank/empty Word in list") {
    val words = List(Word(""))
    assert(WordFunctions.countNumberOfWords(words).count == 0)
  }
  test("countNumberOfUniqueWords should return 1 with a one word list") {
    val words = List(Word("one"))
    assert(WordFunctions.countNumberOfWords(words).count == 1)
  }
  test(s"countNumberOfUniqueWords should handle a random number of words in list") {
    assert(WordFunctions.countNumberOfWords(randomWords).count == randomWords.size)
  }
  test("countNumberOfWordsInMessages should return 0 with a list of empty Messages") {
    val messages = List(
      Message(Id(""), List()),
      Message(Id(""), List()),
    )
    assert(WordFunctions.countNumberOfWordsInMessages(messages).count == 0)
  }
  test("countNumberOfWordsInMessages should return 2 with a list of 2 Messages with 1 Word each") {
    val messages = List(
      Message(Id(""), List(Word("one"))),
      Message(Id(""), List(Word("two")))
    )
    assert(WordFunctions.countNumberOfWordsInMessages(messages).count == 2)
  }
  test("countNumberOfWordsInMessages should return 12 with a list of Messages with Word duplicates") {
    val messages = List(
      Message(Id(""), List(Word("one"), Word("two"), Word("three"), Word("six"))),
      Message(Id(""), List(Word("one"), Word("five"), Word("three"), Word("four"))),
      Message(Id(""), List(Word("one"), Word("seven"), Word("three"), Word("six")))
    )
    assert(WordFunctions.countNumberOfWordsInMessages(messages).count == 12)
  }
}

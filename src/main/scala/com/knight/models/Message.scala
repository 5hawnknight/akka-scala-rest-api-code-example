package com.knight.models

import com.knight.models.tiny.{Id, Word}

case class Message(id: Id, words: List[Word])

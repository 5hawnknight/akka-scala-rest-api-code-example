package com.knight.exceptions

sealed trait APIError {
  val value: String
}

case object DuplicateRequest extends APIError {
  override val value: String = "the request with the same information has been already submitted"
}

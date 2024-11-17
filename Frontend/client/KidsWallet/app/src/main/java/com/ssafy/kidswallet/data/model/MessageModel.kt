package com.ssafy.kidswallet.data.model

data class NoticeResponse(
    val messages: List<MessageModel>
)

data class MessageModel(
    val message: String
)
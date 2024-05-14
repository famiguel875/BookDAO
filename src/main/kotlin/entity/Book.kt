package entity

import java.util.*

data class Book(var id: UUID = UUID.randomUUID(), var titulo: String, var autor: String, var anioPublicacion: Int)
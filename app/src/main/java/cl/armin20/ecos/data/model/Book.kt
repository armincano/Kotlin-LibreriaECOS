package cl.armin20.ecos.data.model

import cl.armin20.ecos.data.local.entities.BookLocal

data class Book(
    val id: Int,
    val author: String,
    val country: String,
    val imageLink: String,
    val language: String,
    val link: String,
    val pages: Int,
    val title: String,
    val year: Int,
    val price: Int,
    val lastPrice: Int,
    val delivery: Boolean
)

//mapping extension function
fun Book.toBookLocal() = BookLocal(
    id = id,
    author = author,
    country = country,
    imageLink = imageLink,
    language = language,
    link = link,
    pages = pages,
    title = title,
    year = year,
    price = price,
    lastPrice = lastPrice,
    delivery = delivery
)
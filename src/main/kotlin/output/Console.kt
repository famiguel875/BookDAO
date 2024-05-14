package output

import entity.Book

class Console : IOutputInfo {
    override fun showMessage(message: String) {
        println(message)
    }

    override fun showBookList(bookList: List<Book>) {
        if (bookList.isNotEmpty()) {
            bookList.forEachIndexed { index, book ->
                println("${index + 1}. ${book.titulo} - ${book.autor} - ${book.anioPublicacion}")
            }
        } else {
            println("No books found!")
        }
    }
}
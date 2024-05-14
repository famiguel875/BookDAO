package output

import entity.Book

interface IOutputInfo {
    fun showMessage(message: String)
    fun showBookList(bookList: List<Book>)
}
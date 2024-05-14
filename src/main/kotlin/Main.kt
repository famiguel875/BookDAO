import dao.BookDAO
import db_connection.DataSourceFactory
import entity.Book
import output.Console
import service.BookService

fun main() {
    val console = Console()
    // Initialize the data source
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    // Initialize BookDAO with Console for logging
    val bookDao = BookDAO(dataSource, console)

    // Initialize BookService
    val bookService = BookService(bookDao)

    // Create a new book
    val newBook = Book(titulo = "El principito", autor = "Antoine de Saint-Exupéry", anioPublicacion = 1943)
    val createdBook = bookService.create(newBook)
    console.showMessage("Created book: $createdBook")

    // Attempt to get the book by ID
    val foundBook = createdBook.let { bookService.getById(it.id) }
    console.showMessage("Found book: $foundBook")

    // Update the book if found
    if (foundBook != null) {
        val updatedBook = foundBook.copy(titulo = "Paz y guerra")
        val savedBook = bookService.update(updatedBook)
        console.showMessage("Updated book: $savedBook")
    } else {
        console.showMessage("No book to update")
    }

    // Create another book
    val otherBook = Book(titulo = "Un mundo feliz", autor = "Aldous Huxley", anioPublicacion = 1932)
    val otherCreatedBook = bookService.create(otherBook)
    console.showMessage("Created other user: $otherCreatedBook")

    // Retrieve and display all books
    val allBooks = bookService.getAll()
    console.showMessage("All books: $allBooks")

    // Delete the first created book if it exists
    createdBook.let {
        if (bookService.delete(it.id)) {
            console.showMessage("Book deleted: ${it.id}")
        } else {
            console.showMessage("Failed to delete book: ${it.id}")
        }
    }

    // Retrieve and display all books after deletion
    val booksAfterDeletion = bookService.getAll()
    console.showBookList(booksAfterDeletion)

    // Delete the other book if it exists
    if (bookService.delete(otherCreatedBook.id)) {
        console.showMessage("Book deleted: ${otherCreatedBook.id}")
    } else {
        console.showMessage("Failed to delete other book")
    }
}
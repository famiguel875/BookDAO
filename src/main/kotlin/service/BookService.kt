package service

import dao.IBookDAO
import entity.Book
import java.util.*

class BookService(private val bookDao: IBookDAO) : IBookService {
    override fun create(book: Book): Book {
        return bookDao.create(book) ?: throw IllegalStateException("Failed to create book")
    }

    override fun getById(id: UUID): Book? {
        return bookDao.getById(id) ?: throw NoSuchElementException("No book found with ID $id")
    }

    override fun update(book: Book): Book {
        if (book.titulo.isBlank()) {
            throw IllegalArgumentException("Book title cannot be blank")
        }
        return bookDao.update(book) ?: throw IllegalStateException("Failed to update book")
    }

    override fun delete(id: UUID): Boolean {
        return bookDao.delete(id) || throw IllegalStateException("Failed to delete book with ID $id")
    }

    override fun getAll(): List<Book> {
        return bookDao.getAll() ?: throw IllegalStateException("Failed to retrieve books")
    }
}

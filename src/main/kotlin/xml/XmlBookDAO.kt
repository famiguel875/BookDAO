package xml

import IFiles
import dao.IBookDAO
import entity.Book
import java.util.*

class XmlBookDAO(private val fileManagement: IFiles, private val filePath: String) : IBookDAO {
    // TODO: Implementar funcionalidad para todas las operaciones CRUD utilizando XML como fuente de datos
    override fun create(book: Book): Book {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Book> {
        TODO("Not yet implemented")
    }

    override fun getById(id: UUID): Book? {
        TODO("Not yet implemented")
    }

    override fun update(book: Book): Book? {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID): Boolean {
        TODO("Not yet implemented")
    }
}
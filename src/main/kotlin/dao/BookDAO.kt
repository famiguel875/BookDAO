package dao

import entity.Book
import output.IOutputInfo
import java.util.*
import javax.sql.DataSource

class BookDAO(
    private val dataSource: DataSource,
    private val console: IOutputInfo
) : IBookDAO {

    override fun create(book: Book): Book? {
        val sql = "INSERT INTO books (id, titulo, autor, anioPublicacion) VALUES (?, ?, ?, ?)"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, book.id.toString())
                stmt.setString(2, book.titulo)
                stmt.setString(3, book.autor)
                stmt.setInt(4, book.anioPublicacion)
                if (stmt.executeUpdate() == 1) {
                    return book
                } else {
                    console.showMessage("*Error* insert query failed!")
                    return null
                }
            }
        }
    }

    override fun getById(id: UUID): Book? {
        val sql = "SELECT * FROM books WHERE id = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id.toString())
                val rs = stmt.executeQuery()
                return if (rs.next()) {
                    Book(
                        id = UUID.fromString(rs.getString("id")),
                        titulo = rs.getString("titulo"),
                        autor = rs.getString("autor"),
                        anioPublicacion = rs.getInt("anioPublicacion")
                    )
                } else null
            }
        }
    }

    override fun getAll(): List<Book>? {
        val sql = "SELECT * FROM books"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                val books = mutableListOf<Book>()
                while (rs.next()) {
                    books.add(
                        Book(
                            id = UUID.fromString(rs.getString("id")),
                            titulo = rs.getString("titulo"),
                            autor = rs.getString("autor"),
                            anioPublicacion = rs.getInt("anioPublicacion")
                        )
                    )
                }
                return books
            }
        }
    }

    override fun update(book: Book): Book? {
        val sql = "UPDATE books SET titulo = ?, autor = ?, anioPublicacion = ? WHERE id = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, book.titulo)
                stmt.setString(2, book.autor)
                stmt.setInt(3, book.anioPublicacion)
                stmt.setString(4, book.id.toString())
                if (stmt.executeUpdate() == 1) {
                    return book
                } else {
                    console.showMessage("*Error* update query failed!")
                    return null
                }
            }
        }
    }

    override fun delete(id: UUID): Boolean {
        val sql = "DELETE FROM books WHERE id = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id.toString())
                return stmt.executeUpdate() == 1
            }
        }
    }
}
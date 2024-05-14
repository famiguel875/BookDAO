import java.io.File

class FileManagement : IFiles {

    override fun existeDir(ruta: String): Boolean {
        return File(ruta).isDirectory
    }

    override fun existeFic(ruta: String): Boolean {
        return File(ruta).isFile
    }

    override fun escribir(fichero: File, info: String): String {
        return try {
            fichero.appendText(info)
            ""
        } catch (e: Exception) {
            "Error al escribir en el archivo: ${e.message}"
        }
    }

    override fun leer(fichero: File): List<String>? {
        return try {
            fichero.readLines()
        } catch (e: Exception) {
            null
        }
    }

    override fun crearDir(ruta: String): String {
        val dirRuta = File(ruta)
        return try {
            if (!dirRuta.isDirectory) {
                if (!dirRuta.mkdirs()) {
                    "No fue posible crear la ruta de directorios"
                } else {
                    ""
                }
            } else {
                ""
            }
        } catch (e: Exception) {
            "Error al crear el directorio $ruta: ${e.message}"
        }
    }

    override fun crearFic(ruta: String, info: String, sobreescribir: Boolean): File? {
        val fichero = File(ruta)
        crearDir(fichero.parent)
        return try {
            if (sobreescribir) {
                fichero.writeText(info)
            } else {
                fichero.createNewFile()
                if (info.isNotEmpty()) {
                    fichero.appendText(info)
                }
            }
            fichero
        } catch (e: Exception) {
            null
        }
    }

    override fun buscarUltimoFicheroEmpiezaPor(directorio: File, nombreFicheroInicio: String): File? {
        val ficheros = directorio.listFiles { fichero ->
            fichero.isFile && fichero.name.startsWith(nombreFicheroInicio)
        }
        return ficheros?.maxByOrNull { it.lastModified() }
    }

    override fun buscarUltimoFicheroEmpiezaFinalizaPor(directorio: File, empiezaPor: String, terminaPor: String): File? {
        val ficheros = directorio.listFiles { fichero ->
            fichero.isFile && fichero.name.startsWith(empiezaPor) && fichero.name.endsWith(terminaPor)
        }
        return ficheros?.maxByOrNull { it.lastModified() }
    }

    override fun buscarInfoFichero(fichero: File, info: String): String? {
        val lineas = leer(fichero)
        return if (lineas != null) {
            lineas.find { it == info.dropLast(1) }
        } else {
            null
        }
    }
}
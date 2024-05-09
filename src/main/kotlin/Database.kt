import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.SQLTimeoutException

/**
 * Objeto `Database` que gestiona la conexión a la base de datos.
 */
object Database {
    // Constantes para la URL, el usuario y la contraseña de la base de datos.
    private const val URL = "jdbc:mysql://localhost:3306/studentdb"
    private const val USER = "studentuser"
    private const val PASSWORD = "password"

    init {
        try {
            // Asegurarse de que el driver JDBC de MySQL esté disponible.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (e: ClassNotFoundException) {
            // Imprime la traza del error si no se encuentra la clase del driver.
            e.printStackTrace();
        }
    }

    /**
     * Método `getConnection` que establece y devuelve la conexión a la base de datos.
     * @return Connection: La conexión a la base de datos.
     * @throws DatabaseTimeoutException: Se lanza cuando la conexión excede el tiempo de espera permitido.
     * @throws SqlErrorException: Se lanza cuando ocurre un error de SQL.
     */
    fun getConnection(): Connection =
        try {
            // Intenta establecer y devolver la conexión a la base de datos.
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLTimeoutException) {
            // Lanza una excepción personalizada cuando la conexión excede el tiempo de espera permitido.
            throw DatabaseTimeoutException("La conexión ha excedido el tiempo de espera permitido.")
        } catch (e: SQLException) {
            // Lanza una excepción personalizada cuando ocurre un error de SQL.
            throw SqlErrorException("Error de SQL: ${e.message}")
        }
}

/**
 * Clase `SqlErrorException` que representa un error de SQL.
 * @param s: El mensaje de error.
 */
class SqlErrorException(s: String) : Throwable() {

}

/**
 * Clase `DatabaseTimeoutException` que representa un tiempo de espera excedido en la conexión a la base de datos.
 * @param s: El mensaje de error.
 */
class DatabaseTimeoutException(s: String) : Throwable() {

}

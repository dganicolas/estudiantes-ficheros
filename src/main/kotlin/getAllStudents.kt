import java.sql.Connection

/**
 * Clase `StudentsRepository` que implementa la interfaz `IStudentsRepository`.
 */
class StudentsRepository : IStudentsRepository {

    /**
     * Método `getAllStudents` que devuelve un objeto `Result` con una lista de nombres de estudiantes.
     * @return Result<List<String>>: Si la operación es exitosa, devuelve `Result.success(students)` donde `students` es una lista de nombres de estudiantes.
     *                               Si ocurre una excepción, devuelve `Result.failure(e)` donde `e` es la excepción ocurrida.
     */
    override fun getAllStudents(): Result<List<String>> {
        return try {
            // Obtiene la conexión a la base de datos.
            val connectionDb = Database.getConnection()

            // Crea una lista mutable para almacenar los nombres de los estudiantes.
            val students = mutableListOf<String>()

            // Usa la conexión a la base de datos.
            connectionDb.use { conn ->
                // Crea una declaración SQL.
                conn.createStatement().use { stmt ->
                    // Ejecuta la consulta SQL que selecciona los nombres de los estudiantes.
                    stmt.executeQuery("SELECT name FROM students").use { rs ->
                        // Itera sobre el conjunto de resultados de la consulta SQL.
                        while (rs.next()) {
                            // Añade el nombre del estudiante al la lista `students`.
                            students.add(rs.getString("name"))
                        }
                    }
                }
            }

            // Devuelve un objeto `Result` con la lista de nombres de estudiantes.
            Result.success(students)
        } catch (e: Exception) {
            // Si ocurre una excepción, devuelve un objeto `Result` con la excepción.
            Result.failure(e)
        }
    }

    /**
     * Método `updateStudents` que actualiza la lista de estudiantes en la base de datos.
     * @param students: Lista de nombres de estudiantes a actualizar en la base de datos.
     * @return Result<Unit>: Si la operación es exitosa, devuelve `Result.success(Unit)`.
     *                       Si ocurre una excepción, devuelve `Result.failure(e)` donde `e` es la excepción ocurrida.
     */
    override fun updateStudents(students: List<String>): Result<Unit> {
        // Declara una variable para la conexión a la base de datos.
        var connectionDb : Connection? = null
        return try {
            // Obtiene la conexión a la base de datos.
            connectionDb = Database.getConnection()

            // Desactiva el auto-commit para manejar las transacciones manualmente.
            connectionDb.autoCommit = false

            // Usa la conexión a la base de datos para crear una declaración SQL y
            // ejecutar una sentencia SQL que elimina todos los registros de la tabla de estudiantes.
            connectionDb.createStatement().use { stmt ->
                stmt.execute("DELETE FROM students")
            }

            // Prepara una sentencia SQL para insertar nombres de estudiantes en la base de datos.
            connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)").use { ps ->
                // Itera sobre la lista de estudiantes.
                for (student in students) {
                    // Establece el nombre del estudiante en la sentencia preparada.
                    ps.setString(1, student)

                    // Ejecuta la actualización en la base de datos.
                    ps.executeUpdate()
                }
            }

            // Confirma las transacciones en la base de datos.
            connectionDb.commit()

            // Devuelve un objeto `Result` con el valor `Unit` si la operación es exitosa.
            Result.success(Unit)
        } catch (e: Exception) {
            // Si ocurre una excepción, revierte las transacciones.
            connectionDb?.rollback()

            // Devuelve un objeto `Result` con la excepción.
            Result.failure(e)
        } finally {
            // Restablece el auto-commit a true.
            connectionDb?.autoCommit = true

            // Cierra la conexión a la base de datos.
            connectionDb?.close()
        }
    }
}
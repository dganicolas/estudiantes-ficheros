import java.sql.Connection

fun getAllStudents(): Result<List<String>> {
    return try {
        val connectionDb = Database.getConnection()
        val students = mutableListOf<String>()
        connectionDb.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery("SELECT name FROM students").use { rs ->
                    while (rs.next()) {
                        students.add(rs.getString("name"))
                    }
                }
            }
        }
        Result.success(students)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun updateStudents(students: List<String>): Result<Unit> {
    var connectionDb : Connection? = null
    return try {
        connectionDb = Database.getConnection()
        connectionDb.autoCommit = false
        connectionDb.createStatement().use { stmt ->
            stmt.execute("DELETE FROM students")
        }
        connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)").use { ps ->
            for (student in students) {
                ps.setString(1, student)
                ps.executeUpdate()
            }
        }
        connectionDb.commit()
        Result.success(Unit)
    } catch (e: Exception) {
        connectionDb?.rollback()
        Result.failure(e)
    } finally {
        connectionDb?.autoCommit = true
        connectionDb?.close()
    }
}
interface IStudentsRepository {
    fun getAllStudents(): Result<List<String>>
    fun updateStudents(students: List<String>): Result<Unit>
}
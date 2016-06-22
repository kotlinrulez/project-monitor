package usecases

interface StatusRepository {
    fun findByName(projectName: String): String?
    fun save(projectName: String, status: String)
}
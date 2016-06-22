package usecases

class InMemoryStatusRepository : StatusRepository {
    private val statuses = mutableMapOf<String, String>()

    override fun save(projectName: String, status: String) {
        statuses.put(projectName, status)
    }

    override fun findByName(projectName: String): String? {
        return statuses[projectName]
    }
}
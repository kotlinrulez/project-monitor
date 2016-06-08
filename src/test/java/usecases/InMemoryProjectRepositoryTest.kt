package usecases

class InMemoryProjectRepositoryTest : ProjectRepositoryTest() {
    override fun createProjectRepository(): ProjectRepository {
        return InMemoryProjectRepository()
    }
}
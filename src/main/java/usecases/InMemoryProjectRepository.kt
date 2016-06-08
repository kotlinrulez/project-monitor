package usecases

import java.util.*

class InMemoryProjectRepository : ProjectRepository {
    val projects = HashMap<String, Project>()
    var currentId = 1L

    override fun save(name: String) : Project {
        val project = Project(name, currentId++)
        projects[name] = project

        return project
    }

    override fun findByName(name: String): Project? {
        return projects[name]
    }

}
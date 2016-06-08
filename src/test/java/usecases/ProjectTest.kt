package usecases

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class ProjectTest {

    @Test
    fun name_is_required() {
        val guiSpy = GuiSpy()
        create_project(name= "", gui=guiSpy, repo = InMemoryProjectRepository())

        assertThat(guiSpy.validationFailedCalls).asList().contains(mapOf("field" to "name", "validation" to "required"))
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(1)
        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(0)
    }

    @Test
    fun save_and_retrieve() {
        val guiSpy = GuiSpy()
        val repo = InMemoryProjectRepository()
        create_project(name = "A project name", gui=guiSpy, repo = repo)

        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(1)
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(0)

        assertThat(repo.findByName(guiSpy.createSucceededCalls[0])).isNotNull()
    }

    @Test
    fun name_is_unique() {
        val repo = InMemoryProjectRepository()
        create_project(name = "A project name", gui=GuiSpy(), repo = repo)

        val guiSpy = GuiSpy()
        create_project(name = "A project name", gui=guiSpy, repo = repo)

        assertThat(guiSpy.validationFailedCalls).asList().contains(mapOf("field" to "name", "validation" to "unique"))
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(1)
        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(0)
    }

    private fun create_project(name: String, gui: Gui, repo: ProjectRepository) {
        if (repo.findByName(name) != null) {
            gui.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "unique"))))
        } else if (name.isEmpty()) {
                gui.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "required"))))
            } else {
                val project = repo.save(name)
                gui.createSucceeded(project.name)
            }
        }
    }

class InMemoryProjectRepositoryTest : ProjectRepositoryTest() {
    override fun createProjectRepository(): ProjectRepository {
        return InMemoryProjectRepository()
    }
}

abstract class ProjectRepositoryTest {

    abstract fun createProjectRepository() : ProjectRepository

    @Test
    fun save_and_retrieve() {
        val repo = createProjectRepository()

        repo.save("twitter for uber")
        repo.save("cats for dogs")
        assertThat(repo.findByName("twitter for uber")).isNotNull()
        assertThat(repo.findByName("cats for dogs")).isNotNull()
    }

    @Test
    fun save_creates_a_unique_id() {
        val repo = createProjectRepository()

        val twitterForUber = repo.save("twitter for uber")
        val catsForDogs = repo.save("cats for dogs")
        assertThat(twitterForUber.id).isGreaterThan(0)
        assertThat(catsForDogs.id).isGreaterThan(0)
        assertThat(twitterForUber.id).isNotEqualTo(catsForDogs.id)
    }
}

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

interface ProjectRepository {

    fun findByName(name: String) : Project?

    fun save(name: String) : Project

}

data class Project(val name: String, val id: Long) {

}

class GuiSpy() : Gui {

    override fun createSucceeded(id: String) {
        createSucceededCalls.add(id)
    }

    override fun validation_failed(errors: ValidationErrors) {
        validationFailedCalls = errors
    }

    var validationFailedCalls: List<Map<String,String>> = listOf()
    val createSucceededCalls = ArrayList<String>()
}

interface Gui {

    fun validation_failed(errors: ValidationErrors)
    fun createSucceeded(id: String)

}

class ValidationErrors(errors: List<Map<String, String>>) : List<Map<String, String>> by errors

package usecases

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class ProjectTest {

    @Test
    fun name_is_required() {
        val guiSpy = GuiSpy()
        create_project(name = "", gui = guiSpy, repo = InMemoryProjectRepository())

        assertThat(guiSpy.validationFailedCalls).asList().contains(mapOf("field" to "name", "validation" to "required"))
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(1)
        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(0)
    }

    @Test
    fun save_and_retrieve() {
        val guiSpy = GuiSpy()
        val repo = InMemoryProjectRepository()
        create_project(name = "A project name", gui = guiSpy, repo = repo)

        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(1)
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(0)

        assertThat(repo.findByName(guiSpy.createSucceededCalls[0])).isNotNull()
    }

    @Test
    fun name_is_unique() {
        val repo = InMemoryProjectRepository()
        create_project(name = "A project name", gui = GuiSpy(), repo = repo)

        val guiSpy = GuiSpy()
        create_project(name = "A project name", gui = guiSpy, repo = repo)

        assertThat(guiSpy.validationFailedCalls).asList().contains(mapOf("field" to "name", "validation" to "unique"))
        assertThat(guiSpy.validationFailedCalls.size).isEqualTo(1)
        assertThat(guiSpy.createSucceededCalls.size).isEqualTo(0)
    }
}


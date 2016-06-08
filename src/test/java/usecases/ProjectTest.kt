package usecases

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Assert
import org.junit.Test

class ProjectTest {

    @Test
    fun name_is_required() {
        val guiSpy = GuiSpy()
        create_project(name= "", gui=guiSpy)

        assertThat(guiSpy.spyValidationErrors).asList().contains(mapOf("field" to "name", "validation" to "required"))
    }

    private fun create_project(name: String, gui: Gui) {
        if (name.isEmpty()) {
            gui.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "required"))))
        }
    }

}

class GuiSpy() : Gui {
    override fun validation_failed(errors: ValidationErrors) {
        spyValidationErrors = errors
    }

    var spyValidationErrors: List<Map<String,String>> = listOf()
}

interface Gui {

    fun validation_failed(errors: ValidationErrors)

}

class ValidationErrors(errors: List<Map<String, String>>) : List<Map<String, String>> by errors

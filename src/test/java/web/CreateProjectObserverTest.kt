package web

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import usecases.ValidationErrors

class CreateProjectObserverTest() {
//    TODO: Check out errors list map thing
    @Test
    fun validation_failed() {
        val errors = ValidationErrors(listOf(mapOf("first" to "error", "second" to "error")))
        val createProjectObserver = CreateProjectObserver()
        createProjectObserver.validation_failed(errors)
        assertThat(createProjectObserver.view).isEqualTo("/projects/new")
    }
}

package web

import org.assertj.core.api.KotlinAssertions
import org.assertj.core.api.KotlinAssertions.assertThat
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import usecases.*

class StatusesControllerTest() {

    private lateinit var statusesController: StatusesController
    private lateinit var mvc: MockMvc
    private lateinit var projectRepo: ProjectRepository
    private lateinit var statusRepository: StatusRepository

    @Before
    fun setup() {
        projectRepo = InMemoryProjectRepository()
        statusRepository = InMemoryStatusRepository()
        statusesController = StatusesController(statusRepository)
        mvc = MockMvcBuilders.standaloneSetup(statusesController).build();
    }

    @Test
    fun retrievingAStatus_whenNoStatus() {
        create_project("TwitterForCats", CreateProjectObserver(), projectRepo)
        mvc.perform(get("/statuses/TwitterForCats")).andExpect { result ->
            assert(result.response.status === 200)
            assert(result.response.contentAsString == "UNKNOWN")
        }
    }

    @Test
    fun retrievingAStatus_whenAStatus() {
        create_project("TwitterForCats", CreateProjectObserver(), projectRepo)
        reportBuildPassed("TwitterForCats", statusRepository)
        mvc.perform(get("/statuses/TwitterForCats")).andExpect { result ->
            assert(result.response.status === 200)
            assert(result.response.contentAsString == "UNKNOWN")
        }
    }
}


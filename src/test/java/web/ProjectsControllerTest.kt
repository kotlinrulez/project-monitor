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
import usecases.InMemoryProjectRepository
import usecases.ProjectRepository

class ProjectsControllerTest() {

    private lateinit var projectsController: ProjectsController
    private lateinit var mvc: MockMvc
    private val projectRepository: ProjectRepository = InMemoryProjectRepository()

    @Before
    fun setup() {
        projectsController = ProjectsController(projectRepository)
        mvc = MockMvcBuilders.standaloneSetup(projectsController).build();
    }

    @Test
    fun new_project() {
        mvc.perform(get("/projects/new"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("Create Project")));
    }

    @Test
    fun create_project() {
        mvc.perform(
                post("/projects").param("name", "Twitter for cats")
        ).andExpect(redirectedUrl("/projects"))

        assertThat(projectRepository.findByName("Twitter for cats")).isNotNull()
    }
}


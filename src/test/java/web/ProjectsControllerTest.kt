package web

import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ProjectsControllerTest() {

    private lateinit var projectsController: ProjectsController
    private lateinit var mvc: MockMvc

    @Before
    fun setup() {
        projectsController = ProjectsController()
        mvc = MockMvcBuilders.standaloneSetup(projectsController).build();
    }

    @Test
    fun new_project() {
        mvc.perform(get("/projects/new"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("Create Project")));
    }
}


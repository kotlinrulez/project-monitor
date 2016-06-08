package web

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import usecases.Gui
import usecases.ProjectRepository
import usecases.ValidationErrors
import usecases.create_project

@Controller
@RequestMapping("projects")
class ProjectsController @Autowired constructor(private val projectRepository: ProjectRepository) {

    @RequestMapping("new")
    @ResponseBody
    fun new(): String {
        return createHTML().html {
            body {
                div {
                    projectForm()
                }
            }
        }
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestParam name: String): String {
        val createProjectObserver = CreateProjectObserver()
        create_project(name, createProjectObserver, projectRepository)
        return createProjectObserver.view
    }

    private fun DIV.projectForm() {
        +"Create Project"
        div {
            form(method = FormMethod.post, action = "/projects") {
                label {
                    +"Project Name"
                    input(type = InputType.text)
                }
            }
        }
    }

}

class CreateProjectObserver : Gui {
    var view: String = ""

    override fun validation_failed(errors: ValidationErrors) {
        throw UnsupportedOperationException()
    }

    override fun createSucceeded(id: String) {
        view = "redirect:/projects"
    }
}

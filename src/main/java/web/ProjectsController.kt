package web

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import usecases.*

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

    @RequestMapping
    @ResponseBody
    fun index(): String {
        val findProjectsObserver = FindProjectsObserver()
        find_projects(observer = findProjectsObserver, repo = projectRepository)
        return createHTML().html {
            body {
                h1 {
                    +"Projects"
                }

                ul {
                    findProjectsObserver.projects.forEach {
                        li {
                            +it.name
                        }
                    }
                }
            }
        }
    }

    private fun find_projects(observer: FindProjectsObserver, repo: ProjectRepository) {
        observer.projects = repo.findAll()
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

class FindProjectsObserver {
    var projects: List<Project> = listOf()
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

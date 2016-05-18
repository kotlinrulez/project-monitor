package web

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("projects")
class ProjectsController {

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

    private fun DIV.projectForm() {
        +"Create Project"
        div {
            form {
                label {
                    +"Project Name"
                    input(type = InputType.text)
                }
            }
        }
    }

}
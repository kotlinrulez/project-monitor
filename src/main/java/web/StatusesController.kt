package web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/statuses")
class StatusesController() {

    @RequestMapping("{projectName}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun find() = "UNKNOWN";
}
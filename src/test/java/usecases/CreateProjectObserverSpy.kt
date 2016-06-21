package usecases

import java.util.*

class CreateProjectObserverSpy() : create_project.Observer {

    override fun createSucceeded(id: String) {
        createSucceededCalls.add(id)
    }

    override fun validation_failed(errors: ValidationErrors) {
        validationFailedCalls = errors
    }

    var validationFailedCalls: List<Map<String,String>> = listOf()
    val createSucceededCalls = ArrayList<String>()
}
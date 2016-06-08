package usecases

interface Gui {

    fun validation_failed(errors: ValidationErrors)
    fun createSucceeded(id: String)

}
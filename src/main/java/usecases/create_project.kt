package usecases

object create_project {

    interface Observer {
        fun validation_failed(errors: ValidationErrors)
        fun createSucceeded(id: String)
    }

    operator fun invoke(name: String, observer: Observer, repo: ProjectRepository) {
        if (repo.findByName(name) != null) {
            observer.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "unique"))))
        } else if (name.isEmpty()) {
            observer.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "required"))))
        } else {
            val project = repo.save(name)
            observer.createSucceeded(project.name)
        }
    }
}

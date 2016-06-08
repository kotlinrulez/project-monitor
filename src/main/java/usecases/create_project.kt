package usecases

fun create_project(name: String, gui: Gui, repo: ProjectRepository) {
    if (repo.findByName(name) != null) {
        gui.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "unique"))))
    } else if (name.isEmpty()) {
        gui.validation_failed(ValidationErrors(listOf(mapOf("field" to "name", "validation" to "required"))))
    } else {
        val project = repo.save(name)
        gui.createSucceeded(project.name)
    }
}
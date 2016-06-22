package usecases

fun getProjectStatus(projectName: String,
                     projectRepo: ProjectRepository,
                     statusRepo: StatusRepository) : GetProjectStatusResult {
    if (projectRepo.findByName(projectName) == null) {
        return GetProjectStatusResult.ProjectNotFound()
    }

    when(statusRepo.findByName(projectName)) {
        null -> return GetProjectStatusResult.Unknown()
        "PASSING" -> return GetProjectStatusResult.Passing()
        "FAILING" -> return GetProjectStatusResult.Failing()
    }

    return GetProjectStatusResult.Unknown()
}
package usecases

fun reportBuildFailed(projectName: String, repo: StatusRepository) {
    repo.save(projectName, "FAILING")
}

fun reportBuildPassed(projectName: String, repo: StatusRepository) {
    repo.save(projectName, "PASSING")
}
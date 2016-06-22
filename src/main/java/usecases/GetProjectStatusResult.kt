package usecases

sealed class GetProjectStatusResult {
    class Unknown(): GetProjectStatusResult()
    class ProjectNotFound(): GetProjectStatusResult()
    class Passing(): GetProjectStatusResult()
    class Failing : GetProjectStatusResult() {}
}
package usecases

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test

class ProjectStatusTest {

    lateinit var projectRepo : ProjectRepository
    lateinit var statusRepo : StatusRepository

    @Before
    fun setUp() {
        projectRepo =  InMemoryProjectRepository()
        statusRepo = InMemoryStatusRepository()
    }

    @Test
    fun getProjectStatus_forStatusWithNoBuilds_isUnknown() {
        projectRepo.save("Twitter for Cats")

        assertThat(getProjectStatus(
                projectName = "Twitter for Cats",
                projectRepo = projectRepo,
                statusRepo = statusRepo)
        ).isInstanceOf(GetProjectStatusResult.Unknown::class.java)
    }

    @Test
    fun getProjectStatus_forProjectNotSaved_isProjectNotFound() {
        assertThat(getProjectStatus(
                projectName = "Twitter for Cats",
                projectRepo = projectRepo,
                statusRepo = statusRepo)
        ).isInstanceOf(GetProjectStatusResult.ProjectNotFound::class.java)
    }

    @Test
    fun getProjectStatus_forProjectWithPreviousPassedBuild_isPassing() {
        projectRepo.save("Twitter for Cats")
        reportBuildPassed("Twitter for Cats", repo=statusRepo)
        assertThat(getProjectStatus(
                projectName = "Twitter for Cats",
                projectRepo = projectRepo,
                statusRepo = statusRepo)
        ).isInstanceOf(GetProjectStatusResult.Passing::class.java)
    }

    @Test
    fun getProjectStatus_forProjectWithPreviousFailedBuild_isPassing() {
        projectRepo.save("Twitter for Cats")
        reportBuildFailed("Twitter for Cats", repo=statusRepo)
        assertThat(getProjectStatus(
                projectName = "Twitter for Cats",
                projectRepo = projectRepo,
                statusRepo = statusRepo)
        ).isInstanceOf(GetProjectStatusResult.Failing::class.java)
    }

    private fun reportBuildFailed(projectName: String, repo: StatusRepository) {
        repo.save(projectName, "FAILING")
    }

    private fun reportBuildPassed(projectName: String, repo: StatusRepository) {
        repo.save(projectName, "PASSING")
    }

    private fun getProjectStatus(projectName: String,
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

    sealed class GetProjectStatusResult {
        class Unknown(): GetProjectStatusResult()
        class ProjectNotFound(): GetProjectStatusResult()
        class Passing(): GetProjectStatusResult()
        class Failing : GetProjectStatusResult() {}
    }
}

package usecases

import org.assertj.core.api.KotlinAssertions
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

abstract class ProjectRepositoryTest {

    abstract fun createProjectRepository() : ProjectRepository

    @Test
    fun save_and_retrieve() {
        val repo = createProjectRepository()

        repo.save("twitter for uber")
        repo.save("cats for dogs")
        assertThat(repo.findByName("twitter for uber")).isNotNull()
        assertThat(repo.findByName("cats for dogs")).isNotNull()
    }

    @Test
    fun save_creates_a_unique_id() {
        val repo = createProjectRepository()

        val twitterForUber = repo.save("twitter for uber")
        val catsForDogs = repo.save("cats for dogs")
        assertThat(twitterForUber.id).isGreaterThan(0)
        assertThat(catsForDogs.id).isGreaterThan(0)
        assertThat(twitterForUber.id).isNotEqualTo(catsForDogs.id)
    }

    @Test
    fun save_and_retrieve_all() {
        val repo = createProjectRepository()

        repo.save("twitter for uber")
        repo.save("cats for dogs")
        val projects = repo.findAll()
        assertThat(projects).asList().isNotEmpty()
        assertThat(projects.map(Project::name)).asList().contains("twitter for uber", "cats for dogs")
    }
}
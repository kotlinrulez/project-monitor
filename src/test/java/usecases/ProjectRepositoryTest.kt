package usecases

import org.assertj.core.api.KotlinAssertions
import org.junit.Test

abstract class ProjectRepositoryTest {

    abstract fun createProjectRepository() : ProjectRepository

    @Test
    fun save_and_retrieve() {
        val repo = createProjectRepository()

        repo.save("twitter for uber")
        repo.save("cats for dogs")
        KotlinAssertions.assertThat(repo.findByName("twitter for uber")).isNotNull()
        KotlinAssertions.assertThat(repo.findByName("cats for dogs")).isNotNull()
    }

    @Test
    fun save_creates_a_unique_id() {
        val repo = createProjectRepository()

        val twitterForUber = repo.save("twitter for uber")
        val catsForDogs = repo.save("cats for dogs")
        KotlinAssertions.assertThat(twitterForUber.id).isGreaterThan(0)
        KotlinAssertions.assertThat(catsForDogs.id).isGreaterThan(0)
        KotlinAssertions.assertThat(twitterForUber.id).isNotEqualTo(catsForDogs.id)
    }
}
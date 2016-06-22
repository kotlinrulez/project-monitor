package usecases

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class StatusRepositoryTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun repos(): List<StatusRepository>{
            return listOf(InMemoryStatusRepository())
        }
    }

    @Parameterized.Parameter
    lateinit var repo: StatusRepository;

    @Test
    fun save_and_retrieve() {
        repo.save("twitter for uber", "PASSING")
        repo.save("cats for dogs", "UH OH")
        assertThat(repo.findByName("twitter for uber")).isEqualTo("PASSING")
        assertThat(repo.findByName("cats for dogs")).isEqualTo("UH OH")
        assertThat(repo.findByName("not persisted status")).isNull()
    }
}
package web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import usecases.InMemoryProjectRepository
import usecases.ProjectRepository

@Configuration
open class Configuration {

    @Bean
    open fun projectRepository(): ProjectRepository {
        return InMemoryProjectRepository()
    }
}
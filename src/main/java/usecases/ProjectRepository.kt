package usecases

interface ProjectRepository {

    fun findByName(name: String) : Project?

    fun save(name: String) : Project

}
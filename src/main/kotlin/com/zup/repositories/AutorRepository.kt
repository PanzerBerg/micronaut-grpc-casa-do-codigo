package com.zup.repositories

import com.zup.model.Autor
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.inject.Singleton

@Repository
interface AutorRepository : CrudRepository<Autor, String> {

}
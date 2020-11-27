package com.zup.model

import com.sun.istack.NotNull
import com.zup.services.validators.EmailUnico
import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
@Introspected
@Suppress("unused")
open class Autor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        open val id: String? = null,
        @field:NotNull
        open val instante: LocalDateTime = LocalDateTime.now(),
        @field:NotNull
        @field:Email
        @EmailUnico(domainClassName = "Autor")
        open val email: String,
        @field:NotNull
        open val nome: String,
        @field:NotNull
        @field:Size(max = 400)
        open val descricao: String
) {

}
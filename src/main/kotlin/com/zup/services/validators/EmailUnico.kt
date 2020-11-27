package com.zup.services.validators

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailUnicoValidator::class])
@MustBeDocumented
annotation class EmailUnico(
        val message: String = "Email já existe",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Any>> = [],
        val field: String = "email",
        val domainClassName: String
)

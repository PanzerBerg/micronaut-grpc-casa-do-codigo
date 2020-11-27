package com.zup.services.validators

import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Singleton
open class EmailUnicoValidator(@PersistenceContext val manager: EntityManager) : ConstraintValidator<EmailUnico, String> {

    private lateinit var domainAttribute: String
    private lateinit var kClassName: String

    override fun initialize(params: EmailUnico) {
        domainAttribute = params.field
        kClassName = params.domainClassName
    }

    @Transactional
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.equals("")) return true

        val query = manager.createQuery("SELECT 1 FROM $kClassName WHERE $domainAttribute = :value")
        query.setParameter("value", value)
        return query.resultList.isEmpty()
    }

}
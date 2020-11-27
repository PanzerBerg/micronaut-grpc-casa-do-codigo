package com.zup.endpoint

import com.zup.AutorGrpc
import com.zup.AutorRequest
import com.zup.AutorResponse
import com.zup.model.Autor
import com.zup.repositories.AutorRepository
import com.zup.services.clients.KafkaClient
import io.grpc.stub.StreamObserver
import io.micronaut.http.annotation.Error
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@Suppress("unused")
open class AutorEndpoint : AutorGrpc.AutorImplBase() {

    private val logger = LoggerFactory.getLogger(AutorEndpoint::class.java)

    @Inject
    open lateinit var repository: AutorRepository
    @Inject
    open lateinit var kafkaClient: KafkaClient

    @Validated
    override fun criarAutor(request: AutorRequest, responseObserver: StreamObserver<AutorResponse>) {
        logger.info("Entrando em criarAutor")

        val autor = Autor(email = request.email, nome = request.nome, descricao = request.descricao)
        logger.info("Novo autor criado com email " +
                "${autor.email.subSequence(0, 4)}*****" +
                "${autor.email.subSequence(autor.email.length - 4, autor.email.length)}")

        repository.save(autor)
        logger.info("Autor salvo no banco de dados.")

        logger.info("Enviando autor por Kafka.")
        kafkaClient.sendAutor(autor.id, autor)
        logger.info("Autor enviado ao Kafka.")

        responseObserver.onNext(AutorResponse.newBuilder().setId(autor.id).build())
        responseObserver.onCompleted()
    }

    @Error(exception = ConstraintViolationException::class)
    fun onConstraintValidationFailed(ex: ConstraintViolationException, responseObserver: StreamObserver<*>) {
        responseObserver.onNext(ex.message as Nothing?)
        responseObserver.onCompleted()
    }

}
package com.zup.services.clients

import com.zup.model.Autor
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
public interface KafkaClient {

    @Topic("autor-kotlin")
    fun sendAutor(@KafkaKey id: String?, autor: Autor)

}
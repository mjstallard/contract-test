package io.pivotal.iad.contracttests

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import feign.slf4j.Slf4jLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration
class RepositoryConfiguration {
    @Bean
    fun foosRepository(env: Environment, mapper: ObjectMapper): FoosRepository  {
        return getFeignBuilder(env, mapper).target(FoosRepository::class.java, "http://localhost:4567")
    }

    @Bean
    public fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        mapper.configOverride(List::class.java).include = JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.ALWAYS)
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper
    }


    private fun getFeignBuilder(env: Environment, mapper: ObjectMapper): Feign.Builder {
        return Feign.builder()
                .logger(Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .client(OkHttpClient())
                .encoder(JacksonEncoder(mapper))
                .decoder(JacksonDecoder(mapper))
    }
}
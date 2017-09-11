package io.pivotal.iad.contracttests

import feign.Headers
import feign.RequestLine

@Headers("Accept: application/json", "Content-Type: application/json")
interface FoosRepository {
    @RequestLine("POST /v1/foos")
    fun create(foo: Foo)
    @RequestLine("GET /v1/foos")
    fun findAll(): List<Foo>
}
package io.pivotal.iad.contracttests

import com.fasterxml.jackson.annotation.JsonProperty


data class Foo(
        @JsonProperty("name")
        val name: String
)
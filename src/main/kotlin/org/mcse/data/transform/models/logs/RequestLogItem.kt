package org.mcse.data.transform.models.logs

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestLogItem(
    val authenticated: String,

    @JsonProperty("request_length")
    val requestLength: Int,

    @JsonProperty("request_content_length")
    val requestContentLength: Int,

    @JsonProperty("request_content_type")
    val requestContentType: String,

    @JsonProperty("request_method")
    val requestMethod: String,

    @JsonProperty("request_uri")
    val requestUri: String,
    val referrer: String,
    val protocol: String,

    @JsonProperty("user_agent")
    val userAgent: String,
)

package org.mcse.data.transform.models.logs

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class UpstreamLogItem(

    @JsonProperty("upstream_response_time")
    val upstreamResponseTime: BigDecimal? = BigDecimal.ZERO,

    @JsonProperty("upstream_response_length")
    val upstreamResponseLength: Int,

    @JsonProperty("upstream_status")
    val upstreamStatus: String,

    @JsonProperty("upstream_connect_time")
    val upstreamConnectionTime: String? = null,

)

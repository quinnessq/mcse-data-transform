package org.mcse.data.transform.models.logs

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ResponseLogItem(
    @JsonProperty("response_body_size")
    val responseBodySize: Int,

    @JsonProperty("response_total_size")
    val responseTotalSize: Int,

    @JsonProperty("response_status")
    val responseStatus: Int,

    @JsonProperty("response_time")
    val responseTime: BigDecimal,

)

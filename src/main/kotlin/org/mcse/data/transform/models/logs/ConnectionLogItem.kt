package org.mcse.data.transform.models.logs

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ConnectionLogItem(
    @JsonProperty("remote_ip")
    val remoteIp: String,

    @JsonProperty("remote_port")
    val remotePort: Int,

    val connection: String,

    @JsonProperty("connection_time")
    val connectionTime: BigDecimal,

)

package org.mcse.data.transform.repository

import org.mcse.data.transform.models.doa.Connection
import org.mcse.data.transform.models.doa.Entry
import org.mcse.data.transform.models.doa.Response
import org.mcse.data.transform.models.doa.Upstream
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UpstreamRepository : JpaRepository<Upstream, Long>

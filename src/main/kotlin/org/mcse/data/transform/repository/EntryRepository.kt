package org.mcse.data.transform.repository

import org.mcse.data.transform.models.doa.Entry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntryRepository : JpaRepository<Entry, Long>

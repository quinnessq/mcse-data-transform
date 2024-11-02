package org.mcse.data.transform.service

import org.hibernate.StaleStateException
import org.mcse.data.transform.models.doa.Entry
import org.mcse.data.transform.repository.ConnectionRepository
import org.mcse.data.transform.repository.EntryRepository
import org.mcse.data.transform.repository.RequestRepository
import org.mcse.data.transform.repository.ResponseRepository
import org.mcse.data.transform.repository.UpstreamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class EntryDomainService(
    private val entryRepository: EntryRepository,
    private val connectionRepository: ConnectionRepository,
    private val responseRepository: ResponseRepository,
    private val requestRepository: RequestRepository,
    private val upstreamRepository: UpstreamRepository,
) {

    @Transactional(rollbackFor = [StaleStateException::class], propagation = Propagation.REQUIRED, readOnly = false)
    fun saveEntity(entry: Entry) {
        val entrySaved = entryRepository.saveAndFlush(entry)
        if(entry.connection != null) connectionRepository.saveAndFlush(entry.connection.copy(entry = entrySaved))
        if(entry.response != null) responseRepository.saveAndFlush(entry.response.copy(entry = entrySaved))
        if(entry.request != null) requestRepository.saveAndFlush(entry.request.copy(entry = entrySaved))
        if(entry.upstream != null) upstreamRepository.saveAndFlush(entry.upstream.copy(entry = entrySaved))
    }
}

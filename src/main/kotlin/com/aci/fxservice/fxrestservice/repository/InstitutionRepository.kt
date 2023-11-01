package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.Institution
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InstitutionRepository : ReactiveCrudRepository<Institution, Long>

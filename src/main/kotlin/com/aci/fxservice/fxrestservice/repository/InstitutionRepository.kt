package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.Institution
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface InstitutionRepository : ReactiveMongoRepository<Institution, Long>

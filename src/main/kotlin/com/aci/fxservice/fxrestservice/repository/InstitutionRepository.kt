package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.Institution
//import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import org.springframework.stereotype.Repository

interface InstitutionRepository : ReactiveMongoRepository<Institution, Long>

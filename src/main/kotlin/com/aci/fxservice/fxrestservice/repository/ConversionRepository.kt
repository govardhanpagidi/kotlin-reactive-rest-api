package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.Conversion
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface ConversionRepository : ReactiveMongoRepository<Conversion, Long>

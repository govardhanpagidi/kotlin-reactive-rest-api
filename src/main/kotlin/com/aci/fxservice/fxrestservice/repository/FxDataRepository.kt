package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.FxData
import com.aci.fxservice.fxrestservice.entity.FxRateKey
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface FxDataRepository : ReactiveMongoRepository<FxData, FxRateKey>{
}
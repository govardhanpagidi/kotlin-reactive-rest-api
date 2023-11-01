package com.aci.fxservice.fxrestservice.controller

import com.aci.fxservice.fxrestservice.model.response.InstitutionResponse
import com.aci.fxservice.fxrestservice.model.request.InstitutionRequest
import com.aci.fxservice.fxrestservice.service.InstitutionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/institutions")
class InstitutionController (
        private val institutionService: InstitutionService
){
    @GetMapping
    fun findAll() : Flux<InstitutionResponse> = institutionService.findInstitutions()

    @GetMapping("/{id}")
    fun findInstitutionById(@PathVariable id:Long) : Mono<InstitutionResponse> = institutionService.findInstitutionById(id)

    @PostMapping
    fun saveInstitution(@RequestBody institutionRequest : InstitutionRequest) : Mono<InstitutionResponse> {
        return institutionService.saveInstitution(institutionRequest)
    }
}
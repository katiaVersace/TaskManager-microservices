package com.alten.teamservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
//import org.springframework.security.web.csrf.CsrfToken

@Api(value = "Task Management System", description = "Default operations in Task Management System")
@RestController
class DefaultController {

    @GetMapping("/")
    @ApiOperation(value = "Home", response = String::class)
    fun getHome(): String = "Employee Service Home"

//    @ApiOperation(value = "CRSF token", response = CsrfToken::class)
//    @GetMapping("/csrf")
//    fun csrf(token: CsrfToken): CsrfToken = token

}
package com.delta.document.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*



@Document(collection = "PolicyHolder")
class PolicyHolder {
    @Id
    var id: String? = null


    var partnerId:String?=null

    var clientName : String? = null

    var clientAddress : String?=null

    var pinCode : String?=null

    var emailId : String?=null

    var mobile : String?=null

    var birthDate: Date? = null

    var aadharNumber: String?=null

    var aadharURL: String?=null

    var panCardNumber: String?=null

    var panCardURL : String?=null

     var  insurance : InsuranceDetails = InsuranceDetails()

    var submissionDate: Date? = null



    constructor()



}
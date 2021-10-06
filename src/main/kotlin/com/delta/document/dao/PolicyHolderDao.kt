package com.delta.document.dao

import com.delta.document.model.InsuranceDetails
import org.springframework.web.multipart.MultipartFile
import java.util.*

class PolicyHolderDao {


    var partnerId:String?=""

    var clientName : String? = ""

    var clientAddress : String?=""

    var pinCode : String?=""

    var emailId : String?=""

    var mobile : String?=""

    var birthDate: Date? = null

    var aadharNumber: String?=""

    var aadharURL: MultipartFile? = null

    var panCardNumber: String?=""

    var panCardURL : MultipartFile?=null

    var  insurance : InsuranceDetailsDao= InsuranceDetailsDao()




    constructor()

    override fun toString(): String {
        return "PolicyHolderDao(partnerId=$partnerId, clientName=$clientName, clientAddress=$clientAddress, pinCode=$pinCode, emailId=$emailId, mobile=$mobile, birthDate=$birthDate, aadharNumber=$aadharNumber, aadharURL=$aadharURL, panCardNumber=$panCardNumber, panCardURL=$panCardURL, insurance=$insurance)"
    }


}
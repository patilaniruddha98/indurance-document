package com.delta.document.dao

import org.springframework.web.multipart.MultipartFile
import java.io.File

class MotorInsuranceS3Request {

    var policyNumber:String=""

    var vehicleRegistrationNumber: String =""

    var vehicleRCURL : File?=null

    var quote : File? =null

    constructor()
}
package com.delta.document.dao

import org.springframework.web.multipart.MultipartFile
import java.io.File

class HealtInsuranceS3Request {

    var policyNumber: String =""

    var quote : File? =null

    var medicalReport: File? =null

    constructor()
}
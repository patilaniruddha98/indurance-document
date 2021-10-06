package com.delta.document.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.delta.document.dao.HealtInsuranceS3Request
import com.delta.document.dao.MotorInsuranceS3Request
import com.delta.document.dao.PolicyHolderDao
import com.delta.document.model.HealthInsurance
import com.delta.document.model.InsuranceDetails
import com.delta.document.model.MotorInsurance
import com.delta.document.model.PolicyHolder
import com.delta.document.repository.PolicyHolderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@Service
class PolicyServiceImpl(
    @Autowired
    val s3Client :AmazonS3,
    @Autowired
    val policyHolderRepository : PolicyHolderRepository
    ) : PolicyService {


    var bucketName: String? = "deltabank/insurance"


//    @Throws(Exception::class)
//    override fun downloadDetailsFile(folder: String?, fileName: String?): ByteArray? {
//
//        val s3Object =
//            s3Client.getObject(bucketName,folder.toString() + "/" + fileName.toString())
//
//        val inputStream = s3Object.objectContent
//        return IOUtils.toByteArray(inputStream)
//    }

    override fun submitDocuments(policyHolderDao: PolicyHolderDao): String? {


        var Baselink="https://deltabank.s3.ap-south-1.amazonaws.com/insurance/"

print(policyHolderDao.partnerId)
        print(policyHolderDao.aadharURL)
        var quoteHealthDoc: File? = null

        var medicalDoc: File? = null
        var vehicleDoc: File? = null

        var quoteMotorDoc: File? = null


        val aadharDoc: File? = policyHolderDao.aadharURL?.let { convertMultipartFileToFile(it) }
        //var aadharDoc: File? = convertMultipartFileToFile(policyHolderDao?.aadharURL!!)

        var aadharDocFileName: String? = Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.aadharURL?.originalFilename
        print(aadharDocFileName+"************")
        val panDoc: File? = policyHolderDao.panCardURL?.let { convertMultipartFileToFile(it) }

        var panDocFileName: String? = Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.panCardURL?.originalFilename


        var healthInput : MutableList<HealthInsurance> = mutableListOf()
        var healtInputS3 : MutableList<HealtInsuranceS3Request> = mutableListOf()
        for (i in 0 until policyHolderDao.insurance.health.size) {
            var p=HealthInsurance()
            var q=HealtInsuranceS3Request()

                q.quote =policyHolderDao.insurance.health[i].quote?.let { convertMultipartFileToFile(it) }

               p.quote=Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.insurance.health[i].policyNumber+"/"+policyHolderDao.insurance.health[i].quote?.originalFilename.toString()
                q.medicalReport = policyHolderDao.insurance.health[i].medicalReport?.let { convertMultipartFileToFile(it) }
               p.medicalReport= Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.insurance.health[i].policyNumber+"/"+policyHolderDao.insurance.health[i].medicalReport?.originalFilename.toString()
            healthInput.add(p)
            healtInputS3.add(q)

        }


        var motorInput : MutableList<MotorInsurance> = mutableListOf()
        var motorInputS3 : MutableList<MotorInsuranceS3Request> = mutableListOf()
        for (i in 0 until policyHolderDao.insurance.motor.size) {

            var p=MotorInsurance()
            var q= MotorInsuranceS3Request()


            q.vehicleRCURL = policyHolderDao.insurance.motor[i].vehicleRCURL?.let { convertMultipartFileToFile(it) }
            p.vehicleRCURL=Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.insurance.motor[i].policyNumber+"/"+policyHolderDao.insurance.motor[i].vehicleRCURL?.originalFilename.toString()

            q.quote = policyHolderDao.insurance.motor[i].quote?.let { convertMultipartFileToFile(it) }
            p.quote=Baselink+policyHolderDao.panCardNumber+"/"+policyHolderDao.insurance.motor[i].policyNumber+"/"+policyHolderDao.insurance.motor[i].quote?.originalFilename.toString()

            motorInput.add(p)
            motorInputS3.add(q)

        }

        var insuranceInput:InsuranceDetails= InsuranceDetails(healthInput,motorInput)


        var policyHolder = PolicyHolder();
        policyHolder.partnerId = policyHolderDao?.partnerId
        policyHolder.clientName = policyHolderDao?.clientName
        policyHolder.clientAddress = policyHolderDao.clientAddress
        policyHolder.pinCode = policyHolderDao.pinCode
        policyHolder.emailId = policyHolderDao.emailId
        policyHolder.mobile = policyHolderDao.mobile
        policyHolder.birthDate = policyHolderDao.birthDate
        policyHolder.aadharNumber = policyHolderDao.aadharNumber
        policyHolder.aadharURL = aadharDocFileName
        policyHolder.panCardNumber = policyHolderDao.panCardNumber
        policyHolder.panCardURL = panDocFileName





        var resHealth: MutableList<HealthInsurance> = mutableListOf()
        for (i in 0 until policyHolderDao.insurance.health.size) {
            println(policyHolderDao.insurance.health.size)


            var p=HealthInsurance()
                println(policyHolderDao.insurance.health[i].policyNumber.toString())

                p.policyNumber = policyHolderDao.insurance.health[i].policyNumber.toString()
                p.quote = insuranceInput.health[i].quote
                p.medicalReport = insuranceInput.health[i].medicalReport
                println(p.policyNumber)
                resHealth.add(p)





        }

        var resMotor: MutableList<MotorInsurance> = mutableListOf()
        for (i in 0 until policyHolderDao.insurance.motor.size) {

           var p = MotorInsurance()
                p.policyNumber = policyHolderDao.insurance.motor[i].policyNumber.toString()
                p.vehicleRegistrationNumber = policyHolderDao.insurance.motor[i].vehicleRegistrationNumber.toString()
                p.quote =insuranceInput.motor[i].quote
                p.vehicleRCURL = insuranceInput.motor[i].vehicleRCURL
                resMotor.add(p)

        }


        var insurance:InsuranceDetails= InsuranceDetails(resHealth,resMotor)
        policyHolder.insurance=insurance

        var date = Date()
        policyHolder.submissionDate = date




try {
    print(policyHolderDao.aadharURL?.originalFilename)
    s3Client.putObject(PutObjectRequest("$bucketName/${policyHolderDao.panCardNumber}", policyHolderDao.aadharURL?.originalFilename, aadharDoc))
    s3Client.putObject(PutObjectRequest("$bucketName/${policyHolderDao.panCardNumber}", policyHolderDao.panCardURL?.originalFilename, panDoc))
    for (i in 0 until policyHolderDao.insurance.health.size) {

        s3Client.putObject(
            PutObjectRequest(
                "$bucketName/${policyHolderDao.panCardNumber}/${policyHolderDao.insurance.health[i].policyNumber.toString()}",
                policyHolderDao.insurance.health[i].quote?.originalFilename.toString(),
                healtInputS3[i].quote
            )
        )
        s3Client.putObject(
            PutObjectRequest(
                "$bucketName/${policyHolderDao.panCardNumber}/${policyHolderDao.insurance.health[i].policyNumber.toString()}",
                policyHolderDao.insurance.health[i].medicalReport?.originalFilename.toString(),
                healtInputS3[i].medicalReport
            )
        )

    }
    for (i in 0 until policyHolderDao.insurance.motor.size) {

        s3Client.putObject(
            PutObjectRequest(
                "$bucketName/${policyHolderDao.panCardNumber}/${policyHolderDao.insurance.motor[i].policyNumber.toString()}",
                policyHolderDao.insurance.motor[i].vehicleRCURL?.originalFilename.toString(),
                motorInputS3[i].vehicleRCURL
            )
        )
        s3Client.putObject(
            PutObjectRequest(
                "$bucketName/${policyHolderDao.panCardNumber}/${policyHolderDao.insurance.motor[i].policyNumber.toString()}",
                policyHolderDao.insurance.motor[i].quote?.originalFilename.toString(),
                motorInputS3[i].quote
            )
        )

    }

    policyHolderRepository.save(policyHolder)
    return "Files uploaded successfully";
}catch (exception:Exception){
    exception.printStackTrace()
    return exception.message

}

    }

    override fun getAllDetails(): MutableList<PolicyHolder?> {
       return policyHolderRepository.findAll()
    }

    override fun getDerailsByPanCardNumber(_id: String?): Optional<PolicyHolder?> {
        return policyHolderRepository.findById(_id!!)
    }


    open fun convertMultipartFileToFile(file: MultipartFile): File {
        val convertedFile = File(file.originalFilename)
        try {
            FileOutputStream(convertedFile).use { fos -> fos.write(file.bytes) }
        } catch (e: IOException) {
            // log.error("Error converting multipart file to file",e);
            e.printStackTrace()
        }
        return convertedFile
    }
}





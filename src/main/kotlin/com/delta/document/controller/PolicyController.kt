package com.delta.document.controller

import com.delta.document.dao.PolicyHolderDao
import com.delta.document.model.PolicyHolder
import com.delta.document.service.PolicyService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.websocket.server.PathParam


@RestController
@RequestMapping("/api/policy")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class PolicyController(
    @Autowired
    val policyService: PolicyService
) {


    @PostMapping("/submitting")
    @Throws(Exception::class)
    fun submit(@PathParam(value="policyHolderDao") policyHolderDao: PolicyHolderDao): String? {
        return policyService.submitDocuments(policyHolderDao)
    }
    
    @GetMapping("/policyholders")
    fun getallclients(): MutableList<PolicyHolder?> {
        return policyService.getAllDetails();
    }

    @GetMapping("/policyholder/{_id}")
    fun getPolicyHolderUsingPan(@PathVariable _id : String?): Optional<PolicyHolder?> {
        return policyService.getDerailsByPanCardNumber(_id!!)
    }


//    @GetMapping("/view/download/{fileName}")
//    fun downloadFile(
//        @PathVariable fileName: String,
//        @RequestParam folder: String?
//    ): ResponseEntity<ByteArrayResource> {
//        val data: ByteArray? = policyService.downloadDetailsFile(folder, fileName)
//        val resource = data?.let { ByteArrayResource(it) }
//        return if (data != null) {
//            ResponseEntity
//                .ok()
//                .contentLength(data.size.toLong())
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attatchment;filename=\"$fileName\"")
//                .body(resource)
//        } else{
//            ResponseEntity.noContent().build()
//        }
//    }

}
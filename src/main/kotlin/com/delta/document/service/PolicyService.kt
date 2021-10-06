package com.delta.document.service


import com.delta.document.dao.PolicyHolderDao
import com.delta.document.model.PolicyHolder
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface PolicyService {


//    @Throws(Exception::class)
//    fun downloadDetailsFile(folder: String?, fileName: String?): ByteArray?

    @Throws(Exception::class)
    fun submitDocuments(
        policyHolderDao: PolicyHolderDao
                        ) : String?

    fun getAllDetails() : MutableList<PolicyHolder?>

    fun getDerailsByPanCardNumber(_id : String?) : Optional<PolicyHolder?>

}
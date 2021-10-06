package com.delta.document

import com.delta.document.dao.PolicyHolderDao
import com.delta.document.model.PolicyHolder
import com.delta.document.repository.PolicyHolderRepository
import com.delta.document.service.PolicyService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.net.URI

import java.util.*


@SpringBootTest()
open class DocumentServiceApplicationTests() {

    @Autowired
    var policyService: PolicyService? = null

    @MockBean
    var policyHolderRepository: PolicyHolderRepository? = null

    @Test
    fun  submitDocumentTest() {

        val date = Date()
        var data = PolicyHolderDao()
        data.partnerId = "6"
        data.clientName = "aniruddha"
        data.clientAddress = "mumbai"
        data.pinCode = "400706"
        data.emailId = "ani@gmail.com"
        data.mobile = "7162986"
        data.birthDate = date
        data.aadharNumber = "12345"
        //data.aadharURL = ani.pdf
        data.panCardNumber = "09876"
        //data.panCardURL ="0987654"


        var d = PolicyHolder()
        d.partnerId = "6"
        d.clientName = "aniruddha"
        d.clientAddress = "mumbai"
        d.pinCode = "400706"
        d.emailId = "ani@gmail.com"
        d.mobile = "7162986"
        d.birthDate = date
        d.aadharNumber = "12345"
        d.aadharURL = "ani.pdf"
        d.panCardNumber = "09876"
        d.panCardURL ="0987654"
        Mockito.`when`(policyHolderRepository!!.save(d)).thenReturn(d)

        Assertions.assertEquals("The key parameter must be specified when uploading an object",policyService!!.submitDocuments(data));

    }


    @Test
    fun getAllDetailsTest(){
        val date = Date()
        var d = PolicyHolder()
        d.partnerId = "6"
        d.clientName = "aniruddha"
        d.clientAddress = "mumbai"
        d.pinCode = "400706"
        d.emailId = "ani@gmail.com"
        d.mobile = "7162986"
        d.birthDate = date
        d.aadharNumber = "12345"
        d.aadharURL = "ani.pdf"
        d.panCardNumber = "09876"
        d.panCardURL ="0987654"

        Mockito.`when`(policyHolderRepository!!.findAll())
            .thenReturn(listOf(d))

        Assertions.assertEquals(1,policyService!!.getAllDetails().size);
    }

  



}


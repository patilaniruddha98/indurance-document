package com.delta.document.dao

import com.delta.document.model.HealthInsurance
import com.delta.document.model.MotorInsurance

class InsuranceDetailsDao {
    var health : ArrayList<HealthInsuranceDao> = ArrayList()

    var motor : ArrayList<MotorInsuranceDao> = ArrayList()

    constructor()
}
package com.delta.document.model

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Field

class InsuranceDetails {

     var health : List<HealthInsurance> = listOf()

     var motor : List<MotorInsurance> = listOf()

     constructor()
     constructor(health: List<HealthInsurance>, motor: List<MotorInsurance>) {
          this.health = health
          this.motor = motor
     }


}
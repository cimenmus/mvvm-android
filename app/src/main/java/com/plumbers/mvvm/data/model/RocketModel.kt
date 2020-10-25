package com.plumbers.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RocketModel(

    @PrimaryKey val
    id: Long = 0,

    @SerializedName("rocket_id")
    val rocketId: String = "",

    @SerializedName("rocket_name")
    val rocketName: String? = "",

    @SerializedName("company")
    val company: String? = "",

    var description: String? = "")


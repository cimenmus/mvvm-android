package com.plumbers.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MissionModel(

    @PrimaryKey
    @SerializedName("mission_id")
    val id: String = "",

    @SerializedName("mission_name")
    val name: String? = "",

    var description: String? = "")
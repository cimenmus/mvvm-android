package com.plumbers.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PersonModel(

    @PrimaryKey
    val id: Int = 0,

    val name: String? = "",
    val biography: String? = "",

    @SerializedName("profile_path")
    val imagePath: String? = "",

)

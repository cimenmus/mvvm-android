package com.plumbers.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieCastModel(

    @PrimaryKey
    val id: Int = 0,

    val name: String = "",
    val character: String? = "",
    val order: Int = 0,
    var movieId: Int = 0,

    @SerializedName("profile_path")
    val imagePath: String? = "",

)

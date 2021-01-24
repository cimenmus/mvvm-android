package com.plumbers.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieModel(

    @PrimaryKey
    val id: Long = -1,

    var adult: Boolean? = false,

    @SerializedName("poster_path")
    val imagePath: String? = "",

    @SerializedName("title")
    val title: String? = "",

    val overview: String? = "",

    @SerializedName("vote_count")
    val averageVote: Double? = 0.0,

    @SerializedName("vote_average")
    val voteCount: Double? = 0.0,

    val popularity: Double? = 0.0,

)
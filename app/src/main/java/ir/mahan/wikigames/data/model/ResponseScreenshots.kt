package ir.mahan.wikigames.data.model


import com.google.gson.annotations.SerializedName

data class ResponseScreenshots(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("height")
        val height: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("is_deleted")
        val isDeleted: Boolean,
        @SerializedName("width")
        val width: Int
    )
}
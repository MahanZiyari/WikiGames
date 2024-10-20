package ir.mahan.wikigames.data.model


import com.google.gson.annotations.SerializedName

data class ResponseStores(
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
        @SerializedName("domain")
        val domain: String?,
        @SerializedName("games")
        val games: List<Game>,
        @SerializedName("games_count")
        val gamesCount: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_background")
        val imageBackground: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
    ) {
        data class Game(
            @SerializedName("added")
            val added: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("slug")
            val slug: String
        )
    }
}
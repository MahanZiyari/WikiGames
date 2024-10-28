package ir.mahan.wikigames.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mahan.wikigames.utils.GAMES_TABLE

@Entity(tableName = GAMES_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var backgroundimage: String = "",
    var genres: List<String> = emptyList(),
    var meta: String = ""
)

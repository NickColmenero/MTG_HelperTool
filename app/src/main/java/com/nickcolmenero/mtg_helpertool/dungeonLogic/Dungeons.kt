package com.nickcolmenero.mtg_helpertool.dungeonLogic

import android.media.Image
import com.nickcolmenero.mtg_helpertool.R

data class DungeonRooms(
    val RoomName: String,
    val RoomID: Int,
    val roomImagelocation: Int,
    val roomRow: Int,
    val roomCanMoveTo:List<Int>,
    val isSelectedRoom: Boolean = false,

)

data class Dungeons(
    val DungeonName: String,
    val RoomSize: Int,
    val DungeonID: Int,
    val roomsInsideDungeon: List<DungeonRooms>
)


val _undercity = Dungeons(
    "The Undercity", 9, 1,
    listOf(
        DungeonRooms("Secret Enterance", 1,R.drawable.secret_entrance,1,listOf(2,3)),
        DungeonRooms("Forge", 2, R.drawable.forge,2,listOf(4,5)),
        DungeonRooms("Lost Well", 3,R.drawable.lost_well,2,listOf(5,6)),
        DungeonRooms("Trap", 4,R.drawable.trap,3,listOf(7)),
        DungeonRooms("Arena", 5,R.drawable.arena,3,listOf(7,8)),
        DungeonRooms("Stash", 6,R.drawable.stash,3,listOf(8)),
        DungeonRooms("Archives", 7,R.drawable.archives,4,listOf(9)),
        DungeonRooms("Catacombs", 8,R.drawable.catacombs,4,listOf(9)),
        DungeonRooms("Throne of the Dead", 9,R.drawable.throne_of_the_dead_three,5,listOf(1))
    )
)
val _BGW = Dungeons(
    "Baldur's Gate Wilderness", 19, 2,
    listOf(
        DungeonRooms("PlaceHolder Haven't made the images yet*", 0,R.drawable.ic_launcher_background,1,listOf(0))
    )
)
val _DMM = Dungeons(
    "Dungeon of the Mad Mage", 9, 3, listOf(
        DungeonRooms("PlaceHolder Haven't made the images yet*", 0,R.drawable.ic_launcher_background,1,listOf(0))
    )
)
val _LMP = Dungeons(
    "Lost Mine of Phandelver", 7, 4, listOf(
        DungeonRooms("PlaceHolder Haven't made the images yet*", 0,R.drawable.ic_launcher_background,1,listOf(0))
    )
)
val _TOA = Dungeons(
    "Tomb of Annihilation", 5, 5, listOf(
        DungeonRooms("PlaceHolder Haven't made the images yet*", 0,R.drawable.ic_launcher_background,1,listOf(0))
    )
)

fun dungeonList(intSelectedDungeonID: Int): Dungeons {
    // This is in case I forget to add a specific dungeon it defaults to this
    val nullDungeon = Dungeons("", 100, 0, listOf(
        DungeonRooms(
            "PlaceHolder Haven't made the images yet*",
            0,R.drawable.ic_launcher_background,
            1,
            listOf(0)
        )
    ))
    var selectectedDungeon: Dungeons
    val DunList = listOf(nullDungeon, _undercity, _BGW, _DMM, _LMP, _TOA)
    selectectedDungeon = DunList.find { it.DungeonID == intSelectedDungeonID } ?: nullDungeon
    return selectectedDungeon
}
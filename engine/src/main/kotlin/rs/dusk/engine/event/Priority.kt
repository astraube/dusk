package rs.dusk.engine.event

object Priority {
    private var counter = 100
    val LOGIN_QUEUE = counter--
    val VIEWPORT = counter--
    val PLAYER_MOVEMENT = counter--
    val NPC_MOVEMENT = counter--
    val PLAYER_VISUALS = counter--
    val NPC_VISUALS = counter--
    val PLAYER_CHANGE = counter--
    val NPC_CHANGE = counter--
    val PLAYER_UPDATE = counter--
    val NPC_UPDATE = counter--
    val PLAYER_UPDATE_FINISHED = counter--
    val NPC_UPDATE_FINISHED = counter--

    val HIGHEST = 10
    val HIGH = 5
    val NORMAL = 0
    val LOW = -5
    val LOWEST = -10
}
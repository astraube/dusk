package rs.dusk.engine.entity.character.player

import com.fasterxml.jackson.annotation.JsonProperty
import rs.dusk.engine.action.Action
import rs.dusk.engine.client.ui.InterfaceOptions
import rs.dusk.engine.client.ui.Interfaces
import rs.dusk.engine.client.ui.dialogue.Dialogues
import rs.dusk.engine.entity.Size
import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.CharacterEffects
import rs.dusk.engine.entity.character.CharacterValues
import rs.dusk.engine.entity.character.contain.Container
import rs.dusk.engine.entity.character.move.Movement
import rs.dusk.engine.entity.character.player.delay.Delays
import rs.dusk.engine.entity.character.player.req.Requests
import rs.dusk.engine.entity.character.player.skill.Experience
import rs.dusk.engine.entity.character.player.skill.Levels
import rs.dusk.engine.entity.character.update.LocalChange
import rs.dusk.engine.entity.character.update.Visuals
import rs.dusk.engine.map.Tile
import rs.dusk.engine.path.TargetStrategy

/**
 * A player controlled by client or bot
 * @author Tyluur <itstyluur@gmail.com
 * @author Greg Hibberd <greg@greghibberd.com>
 *
 * @since March 28, 2020
 */
class Player(
	
	override var id : Int = -1,
	
	/**
	 * The string used to log in
	 */
	@JsonProperty("login")
	var login : String = "",
	
	override var index : Int = -1,
	
	@JsonProperty("tile")
	override var tile : Tile = Tile.EMPTY

) : Character {
	
	override var size : Size = Size.TILE
	
	override val effects = CharacterEffects(this)
	
	val requests : Requests = Requests(this)
	
	val options = PlayerOptions(this)
	
	val gameFrame = PlayerGameFrame()
	
	val delays : Delays = Delays()
	
	val viewport : Viewport = Viewport()
	
	val dialogues : Dialogues = Dialogues()
	
	lateinit var interfaces : Interfaces
	
	lateinit var interfaceOptions : InterfaceOptions
	
	override lateinit var interactTarget : TargetStrategy
	
	lateinit var followTarget : TargetStrategy
	
	override var change : LocalChange? = null
	
	var changeValue : Int = -1
	
	override val visuals : Visuals = Visuals()
	
	override val movement : Movement = Movement(tile)
	
	override val action : Action = Action()
	
	val containers : MutableMap<Int, Container> = mutableMapOf()
	
	val variables : MutableMap<Int, Any> = mutableMapOf()
	
	override val values : CharacterValues = CharacterValues()
	
	@JsonProperty("experience")
	val experience : Experience = Experience()
	
	@JsonProperty("levels")
	val levels : Levels = Levels(experience)
	fun start() {
		options.set(2, "Follow")
		options.set(4, "Trade with")
		options.set(7, "Req Assist")
	}
	
	override fun equals(other : Any?) : Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		other as Player
		return index == other.index
	}
	
	override fun hashCode() : Int {
		return index
	}
	
	override fun toString() : String {
		return "Player($login, index=$index, tile=$tile)"
	}
}
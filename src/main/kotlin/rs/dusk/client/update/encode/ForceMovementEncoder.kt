package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.engine.entity.character.update.visual.ForceMovement
import rs.dusk.game.entity.character.update.VisualEncoder

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class ForceMovementEncoder(private val npc : Boolean, mask : Int) : VisualEncoder<ForceMovement>(mask) {
	
	override fun encode(writer : Writer, visual : ForceMovement) {
		val (tile1, delay1, tile2, delay2, direction) = visual
		writer.apply {
			writeByte(tile1.x, Modifier.SUBTRACT)
			writeByte(tile1.y, if (npc) Modifier.SUBTRACT else Modifier.NONE)
			writeByte(tile2.x, if (npc) Modifier.INVERSE else Modifier.ADD)
			writeByte(tile2.y, if (npc) Modifier.INVERSE else Modifier.ADD)
			writeShort(delay1, order = if (npc) Endian.BIG else Endian.LITTLE)
			writeShort(delay2, Modifier.ADD, Endian.LITTLE)
			writeByte(direction.ordinal / 2, if (npc) Modifier.SUBTRACT else Modifier.ADD)
		}
	}
	
}
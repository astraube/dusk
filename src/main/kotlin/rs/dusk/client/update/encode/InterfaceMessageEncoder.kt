package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.packet.access.PacketWriter
import rs.dusk.network.codec.game.GameMessageEncoder
import rs.dusk.network.codec.game.encode.message.InterfaceAnimationMessage
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_ANIMATION

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 02, 2020
 */
class InterfaceMessageEncoder : GameMessageEncoder<InterfaceAnimationMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceAnimationMessage) {
        val (id, component, animation) = msg
        builder.apply {
            writeOpcode(opcode = INTERFACE_ANIMATION)
            writeShort(animation, Modifier.ADD, Endian.LITTLE)
            writeInt(id shl 16 or component, order = Endian.MIDDLE)
        }
    }
}
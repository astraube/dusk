package rs.dusk.engine.client.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.cache.definition.decoder.InterfaceDecoder
import rs.dusk.core.network.model.session.getSession
import rs.dusk.engine.client.Sessions
import rs.dusk.engine.client.ui.InterfacesLookup
import rs.dusk.engine.client.ui.event.InterfaceInteraction
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.task.TaskExecutor
import rs.dusk.engine.task.start
import rs.dusk.network.rs.codec.game.GameMessageHandler
import rs.dusk.network.rs.codec.game.decode.message.InterfaceOptionMessage
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 26, 2020
 */
class InterfaceOptionMessageHandler : GameMessageHandler<InterfaceOptionMessage>() {

    val sessions: Sessions by inject()
    val bus: EventBus by inject()
    val executor: TaskExecutor by inject()
    val decoder: InterfaceDecoder by inject()
    val lookup: InterfacesLookup by inject()
    val logger = InlineLogger()

    override fun handle(ctx: ChannelHandlerContext, msg: InterfaceOptionMessage) {
        val session = ctx.channel().getSession()
        val player = sessions.get(session) ?: return
        val (hash, one, two, option) = msg

        val id = hash shr 16
        if (!player.interfaces.contains(id)) {
            logger.warn { "Interface $id not found for player $player" }
            return
        }

        val componentId = hash and 0xffff
        val definition = decoder.getSafe(id)
        val component = definition.components?.get(componentId)
        if(component == null) {
            logger.warn { "Interface $id component $componentId not found for player $player" }
            return
        }

        val options = component.options ?: return
        val index = option - 1
        if (index !in options.indices) {
            logger.warn { "Interface $id component $componentId option $index not found for player $player" }
            return
        }

        val name = lookup.get(id).name ?: ""
        val selectedOption = options[index]
        executor.start {
            bus.emit(InterfaceInteraction(player, id, name, componentId, selectedOption, index, one, two))
        }
    }

}
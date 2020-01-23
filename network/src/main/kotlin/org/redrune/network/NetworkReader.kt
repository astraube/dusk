package org.redrune.network

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.ReferenceCountUtil
import org.redrune.network.session.Session
import org.slf4j.LoggerFactory
import java.util.logging.Level

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since January 22, 2020
 */
class NetworkReader : ChannelInboundHandlerAdapter() {

    /**
     * The logger for this class
     */
    private val logger = LoggerFactory.getLogger(NetworkReader::class.java)

    override fun channelActive(ctx: ChannelHandlerContext) {
        logger.info("Channel connected: " + ctx.channel().remoteAddress() + ".")
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        try {
            println("channelRead:\t[${msg}]")
            val channel = ctx.channel()
            val session = channel.attr(Session.SESSION_KEY).get()
            session?.messageReceived(msg)
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, e: Throwable) {
        if (!e.message!!.contains("An existing connection was forcibly closed by the remote host")) {
            logger.error("Exception occurred for channel: " + ctx.channel() + ", closing...", e)
        }
        ctx.channel().close()
    }

}
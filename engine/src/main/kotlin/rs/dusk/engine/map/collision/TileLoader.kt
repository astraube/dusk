package rs.dusk.engine.map.collision

import rs.dusk.core.io.read.BufferReader

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 16, 2020
 */
class TileLoader {

    fun load(data: ByteArray): Array<Array<ByteArray>> {
        val buffer = BufferReader(data)
        var config: Int
        val tileSettings = Array(4) { Array(64) { ByteArray(64) } }
        val i = 0
        //For every region tile
        for (plane in 0 until 4) {
            for (localX in 0 until 64) {
                for (localY in 0 until 64) {
                    loop@ while (true) {
                        config = buffer.readUnsignedByte()
                        when {
                            config == 0 -> break@loop
                            config == 1 -> {
                                val height = buffer.readUnsignedByte()
                                break@loop
                            }
                            config <= 49 -> {
                                val attrOpcode = config
                                val overlayId = buffer.readByte()
                                val overlayPath = (config - 2) / 4
                                val overlayRotation = 3 and (config - 2 + i)
                            }
                            config <= 81 -> {
                                val settings = (config - 49).toByte()
                                tileSettings[plane][localX][localY] = settings
                            }
                            else -> {
                                val underlayId = config - 81
                            }
                        }
                    }
                }
            }
        }
        return tileSettings
    }
}
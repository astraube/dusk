package rs.dusk.network.codec.download.decode.message

import rs.dusk.network.codec.download.DownloadServiceMessage

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class DownloadDisconnected(val value: Int) : DownloadServiceMessage
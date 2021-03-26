package rs.dusk.engine.storage

import java.sql.Connection

/**
 * This class provides the storage functionality for all data that will be saved in a sql database
 *
 * @author Tyluur <itstyluur@icloud.com>
 * @since August 12, 2020
 */
interface Storable {
	
	/**
	 * This function handles reading from the database
	 */
	fun read(connection : Connection) : Any
	
	/**
	 * This function handles writing to the database
	 */
	fun write(connection : Connection)
	
	
}
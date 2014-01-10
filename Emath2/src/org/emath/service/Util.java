package org.emath.service;

import org.emath.model.book.AddedContent.ContentType;

/**
 * Generic utils.
 *
 */
public class Util {
	
	// Used in separating timestamp and data parts in the content sent to the
		// client
		public static String TIMESTAMP_DELIMITER = "_";

	/**
	 * Tries to parse content type from the tiddler data sent by the client.
	 * Returns AddedContent.ContentType.EXAMPE, if no content type was found.
	 * 
	 * @param data
	 * @return
	 */
	public static ContentType parseContentType(String data) {
		String bq = "&quot;type&quot;:&quot;";
		String eq = "&quot;";

		if (data.indexOf(bq + ContentType.EXAMPLE.getStringValue() + eq) > -1) {
			return ContentType.EXAMPLE;
		}
		if (data.indexOf(bq + ContentType.SD_MARKINGS.getStringValue() + eq) > -1) {
			return ContentType.SD_MARKINGS;
		}
		if (data.indexOf(bq + ContentType.HOME_ASSIGNMENTS.getStringValue()
				+ eq) > -1) {
			return ContentType.HOME_ASSIGNMENTS;
		}
		if (data.indexOf(bq + ContentType.MODEL_SOLUTION.getStringValue() + eq) > -1) {
			return ContentType.MODEL_SOLUTION;
		}
		if (data.indexOf(bq + ContentType.ASSIGNMENT.getStringValue() + eq) > -1) {
			return ContentType.ASSIGNMENT;
		}
		if (data.indexOf(bq + ContentType.TEXT.getStringValue() + eq) > -1) {
			return ContentType.TEXT;
		}
		if (data.indexOf(bq + ContentType.THEORY.getStringValue() + eq) > -1) {
			return ContentType.THEORY;
		}

		return ContentType.EXAMPLE;
	}

	/**
	 * Parses the value of solutionto attribute from the solution data sent by
	 * the client. solution to attribute indicates the name of the assignment
	 * tiddler in client book for which this solutions is an answer for.
	 * 
	 * @param data
	 * @return
	 */
	public static String parseSolutionTo(String data) {
		if (data.indexOf("solutionto=\"") > -1) {
			String start = data.substring(data.indexOf("solutionto=\"") + 12);
			return start.substring(0, start.indexOf("\""));
		}
		return "";
	}
}

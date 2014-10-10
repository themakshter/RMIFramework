

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class which parses the XML files of the standings and matches and returns
 * HashMaps
 * 
 * @author mak1g11
 * 
 */
public class XMLParser {
	private static HashMap<String, String> map;

	/**
	 * Parses the XML files containing the league standings
	 * @return HashMap from the leagues to the standings
	 */
	public static HashMap<String, String> getStandings() {
		try {
			map = new HashMap<String, String>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean bleague = false;
				boolean bname = false;
				boolean bfullname = false;
				boolean bposition = false;
				boolean bpoints = false;
				boolean bplayed = false;
				boolean bwon = false;
				boolean bdrawn = false;
				boolean blost = false;
				boolean bscored = false;
				boolean bconceeded = false;
				String standing = "\t\t\tWon\tDrawn\tLost\tFor\tAgainst\tPoints";
				String key = "";
				String value;
				String rows = "";
				String points = "";

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// System.out.println( "Start Element :" + qName);

					if (qName.equalsIgnoreCase("LEAGUE")) {
						bleague = true;
					}

					if (qName.equalsIgnoreCase("NAME")) {
						bname = true;
					}

					if (qName.equalsIgnoreCase("FULLNAME")) {
						bfullname = true;
					}

					if (qName.equalsIgnoreCase("POSITION")) {
						bposition = true;
					}

					if (qName.equalsIgnoreCase("POINTS")) {
						bpoints = true;
					}

					if (qName.equalsIgnoreCase("PLAYED")) {
						bplayed = true;
					}

					if (qName.equalsIgnoreCase("WON")) {
						bwon = true;
					}

					if (qName.equalsIgnoreCase("DRAWN")) {
						bdrawn = true;
					}

					if (qName.equalsIgnoreCase("LOST")) {
						blost = true;
					}

					if (qName.equalsIgnoreCase("SCORED")) {
						bscored = true;
					}

					if (qName.equalsIgnoreCase("CONCEEDED")) {
						bconceeded = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (bleague) {
						value = "";
						key = new String(ch, start, length);
						value = standing + value;
						map.put(key, value);
						bleague = false;
					}

					if (bname) {
						rows = "";
						rows += ". " + new String(ch, start, length);
						bname = false;
					}

					if (bfullname) {
						bfullname = false;
					}

					if (bposition) {
						rows = new String(ch, start, length) + "" + rows;
						bposition = false;
					}

					if (bpoints) {
						points = new String(ch, start, length);
						bpoints = false;
					}

					if (bplayed) {
						String stuff = new String(ch, start, length);
						if (rows.length() > 15) {
							rows += "\t" + stuff;
						} else if (rows.length() < 8) {
							rows += "\t\t\t" + stuff;
						} else {
							rows += "\t\t" + stuff;
						}
						bplayed = false;
					}

					if (bwon) {
						rows += "\t" + new String(ch, start, length);
						bwon = false;
					}

					if (bdrawn) {
						rows += "\t" + new String(ch, start, length);
						bdrawn = false;
					}

					if (blost) {
						blost = false;
					}

					if (bscored) {
						rows += "\t" + new String(ch, start, length);
						bscored = false;
					}

					if (bconceeded) {
						rows += "\t" + new String(ch, start, length);
						value += "\n" + rows + "\t" + points;
						map.put(key, value);
						points = "";
						bconceeded = false;
					}
				}

			};

			File file = new File("football/standings.xml");
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			saxParser.parse(is, handler);
			map.remove(""); // remove the first case which is an empty string
							// mapping
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Parses the XML file containing the league match results
	 * @return HashMap linking each league to the results
	 */
	public static HashMap<String, String> getMatches() {
		try {
			map = new HashMap<String, String>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bleague = false;
				boolean bhome = false;
				boolean baway = false;
				boolean bscore = false;
				boolean btime = false;
				String key = "";
				String value = "";
				String home = "";
				String away = "";
				String result = "";
				String score = "";

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("LEAGUE")) {
						bleague = true;
					}

					if (qName.equalsIgnoreCase("HOME")) {
						bhome = true;
					}

					if (qName.equalsIgnoreCase("AWAY")) {
						baway = true;
					}

					if (qName.equalsIgnoreCase("SCORE")) {
						bscore = true;
					}

					if (qName.equalsIgnoreCase("TIME")) {
						btime = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (bleague) {
						result = "";
						key = new String(ch, start, length);
						map.put(key, value);
						bleague = false;
					}

					if (bhome) {
						home = " " + new String(ch, start, length);
						bhome = false;
					}

					if (baway) {
						away = " " + new String(ch, start, length);
						baway = false;
					}

					if (bscore) {
						score += " " + new String(ch, start, length);
						bscore = false;
					}

					if (btime) {

						result += home + score + away + " "
								+ new String(ch, start, length) + "\n";
						map.put(key, value + result);
						home = "";
						away = "";
						score = "";
						btime = false;
					}
				}
			};

			File file = new File("football/matches.xml");
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			return map;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

package File_format;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import GIS.Element;
import GIS.GIS_element;
import GIS.Layer;
//import GIS.Element;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;
/**
 *responsible of writing the final kml file 
 */
public class Csv2Kml {
	public static void main(String [] args) {
		
			read_and_store_csv("C:\\Users\\nour\\Desktop\\my_kml\\test.csv","C:\\Users\\nour\\Desktop\\my_kml\\tesaaaaaaaat.kml");


	}

	public static void read_and_store_csv(String csvPath, String destPath) {

		try {
			 List<String> lines = Files.readAllLines(Paths.get(csvPath));

			Iterator<String> it = lines.iterator();
			Element ele;
			Layer lay =new Layer();
			it.next(); it.next();
			while(it.hasNext()) {
				ele = new Element(it.next());
				lay.add(ele);
			}

			makeKML(lay, destPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * creating the kml and sending it to directory
	 * @param toDisplay
	 */
	public static void makeKML(ArrayList<GIS_element> toDisplay,String path) {
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		Icon icon = new Icon()
			    .withHref("http://maps.google.com/mapfiles/ms/icons/red-dot.png");
		Style style = doc.createAndAddStyle();
		style.withId("style_" + "red") // set the stylename to use this style from the placemark
		    .createAndSetIconStyle().withScale(1.0).withIcon(icon); // set size and icon
		style.createAndSetLabelStyle().withColor("ff43b3ff").withScale(0.5); // set color and size of the continent name
		
		String time;

		for (int i = 0; i < toDisplay.size(); i++) {
			Element w = (Element) toDisplay.get(i);
			time = convertTimeFormat(w.getTime());
			TimeStamp ts = new TimeStamp();
			ts.setWhen(time);
			doc.createAndAddPlacemark().withName(w.getSsid()).withOpen(true).withTimePrimitive(ts)
			.withStyleUrl("#style_" + "red")
			.withDescription("mac: " + w.getMac() + "<br/> freq: " + w.getFreq() + "<br/> signal: " + w.getSignal() + "<br/> Date: " + w.getTime())
			.createAndSetPoint().addToCoordinates(Double.parseDouble(w.getLon()),
					Double.parseDouble(w.getLat()), Double.parseDouble(w.getAlt()));
		}
		try {
			kml.marshal(new File(path));
		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}


	/**
	 *converting the time format from yyyy\MM\dd hh:mm:ss to yyyy\MM\dd+T+hh:mm:ss{ google earth format}
	 * @param oldTimeFormat date time in old format yyyy\MM\dd hh:mm:ss
	 * @return the new format yyyy\MM\dd+T+hh:mm:ss
	 */
	private static String convertTimeFormat(String oldTimeFormat) {
		String[] dateTime = oldTimeFormat.split(" ");
		return dateTime[0] + 'T' + dateTime[1];
	}
}

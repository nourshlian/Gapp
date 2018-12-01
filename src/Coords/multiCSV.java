package Coords;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import GIS.Element;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.Layer;
import GIS.Project;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

public class multiCSV {
	static List<String> filenames = new ArrayList<String>();
	static Element ele;
	static GIS_layer lay =new Layer();
	static GIS_project pro = new Project() ;
	public static void main(String[] args) {
		String filepath = "C:\\Users\\nour\\Desktop\\my_kml";
		listFilesForFolder(new File(filepath));
		Iterator<?> it = filenames.iterator();
		while(it.hasNext()) {
			read_csv((String)it.next() , filepath);
		}
		//System.out.println(filenames.toString());
		makeKML((ArrayList<GIS_layer>) pro, "C:\\Users\\nour\\Desktop\\my_kml\\multi kml.kml");
	}
	public static void read_csv(String filename , String filepath) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filepath+"\\"+filename));
			Iterator<String> it = lines.iterator();

			it.next(); it.next();
			while(it.hasNext()) {
				ele = new Element(it.next());
				lay.add(ele);
			}
			pro.add(lay);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void listFilesForFolder(File folder) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if(fileEntry.getName().contains(".csv"))
					filenames.add(fileEntry.getName());
			}
		}
	}

	public static void makeKML(ArrayList<GIS_layer> play,String path) {
		final Kml kml = new Kml();
		Iterator<GIS_layer> it = play.iterator();
		Document doc = kml.createAndSetDocument();
		Icon red = new Icon()
			    .withHref("http://maps.google.com/mapfiles/ms/icons/red-dot.png");
		Style redstyle = doc.createAndAddStyle();
		redstyle.withId("style_" + "red") // set the stylename to use this style from the placemark
		    .createAndSetIconStyle().withScale(1.0).withIcon(red); // set size and icon
		redstyle.createAndSetLabelStyle().withColor("ff43b3ff").withScale(0.5); // set color and size of the continent name
		Icon green = new Icon()
			    .withHref("http://maps.google.com/mapfiles/kml/paddle/grn-blank.png");
		Style greenstyle = doc.createAndAddStyle();
		greenstyle.withId("style_" + "green") // set the stylename to use this style from the placemark
		    .createAndSetIconStyle().withScale(1.0).withIcon(green); // set size and icon
		greenstyle.createAndSetLabelStyle().withColor("37FF33").withScale(0.5); // set color and size of the continent name
		
		String time;
		String icon = null;
		String[] color = {"green" , "red"};
		int i = 0;
		while(it.hasNext()) {
			icon = color[++i % 2];
			Layer toDisplay = (Layer) it.next();
			for (int j = 0; j < toDisplay.size(); j++) {
				Element w = (Element) toDisplay.get(j);
				time = convertTimeFormat(w.getTime());
				TimeStamp ts = new TimeStamp();
				System.out.println(icon);
				ts.setWhen(time);
				doc.createAndAddPlacemark().withName(w.getSsid()).withOpen(Boolean.TRUE).withTimePrimitive(ts)
				.withStyleUrl("#style_" + icon) 
				.withDescription("mac: " + w.getMac() + "<br/> freq: " + w.getFreq() + "<br/> signal: " + w.getSignal() + "<br/> Date: " + w.getTime())
				.createAndSetPoint().addToCoordinates(Double.parseDouble(w.getLon()),
						Double.parseDouble(w.getLat()), Double.parseDouble(w.getAlt()));
			}
		}
		try {
			kml.marshal(new File(path));
		} catch (IOException ex) {
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}
	private static String convertTimeFormat(String oldTimeFormat) {
		String[] dateTime = oldTimeFormat.split(" ");
		return dateTime[0] + 'T' + dateTime[1];
	}

}

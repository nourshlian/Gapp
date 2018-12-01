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
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

public class multiCSV {
	static List<String> filenames = new ArrayList<String>();
	static Element ele;
	static GIS_layer lay =new Layer();
	static GIS_project pro = null ;
	public static void main(String[] args) {
		String filepath = "C:\\Users\\nour\\Desktop\\my_kml";
		listFilesForFolder(new File(filepath));
		Iterator<?> it = filenames.iterator();
		while(it.hasNext()) {
			read_csv((String)it.next() , filepath);
		}
		//System.out.println(filenames.toString());
		makeKML((ArrayList<GIS_layer>) pro, "C:\\Users\\nour\\Desktop\\my_kml\\multikml.kml");
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

	public static void makeKML(ArrayList<GIS_layer> toDisplay,String path) {
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument();
		String time;
		for (int i = 0; i < toDisplay.size(); i++) {
			for (int j = 0; j < toDisplay.get(i).size(); j++) {
				Element w = (Element) (((GIS_layer) toDisplay.get(i)));
				time = convertTimeFormat(w.getTime());
				TimeStamp ts = new TimeStamp();
				ts.setWhen(time);
				doc.createAndAddPlacemark().withName(w.getSsid()).withOpen(Boolean.TRUE).withTimePrimitive(ts)
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

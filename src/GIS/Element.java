package GIS;

import Geom.GeomElement;
import Geom.Geom_element;
import Geom.Point3D;
import Coords.MyCoords;
import Coords.coords_converter;
public class Element implements GIS_element{
	private String  lat, lon, alt, signal, time, type, mac, ssid, freq;
	private int channel ;
//	private Point3D pp;

	public Element() {
	}
	// get a line of csv file
	public Element(String line){
		String[] temp = line.split(",");
		this.mac = temp[0];
		this.ssid = temp[1];
		this.time = temp[3];
		this.signal = temp[5];
		this.lat = temp[6];
		this.lon = temp[7];
		this.alt = temp[8];
		this.type = temp[10];

		channel = Integer.parseInt(temp[4]);
		int i;
		if (channel >= 1 && channel <= 14) {
			i = (channel - 1) * 5 + 2412;
			this.freq = i + "";
		} else {
			if (channel >= 36 && channel <= 165) {
				i = (channel - 34) * 5 + 5170;
				this.freq = i + "";
			} else
				this.freq = "";
		}
		MetaData m = new MetaData(this) ;
	}
	public Element( String mac, String ssid, String freq, String lat,
			String lon, String alt, String signal,String time) {

		this.freq = freq;
		this.mac = mac;
		this.ssid = ssid;
		this.time = time;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.signal = signal;
	}
	public Element(String mac, String ssid, String freq, String signal, String time) {

		this.freq = freq;
		this.mac = mac;
		this.ssid = ssid;
		this.time = time;
		this.signal = signal;
	}


	@Override
	public Geom_element getGeom() {
		double lat = Double.parseDouble(this.lat);
		double lon = Double.parseDouble(this.lon);
		double alt = Double.parseDouble(this.alt);
		GeomElement GE = new GeomElement(new Point3D(lat,lon,alt));
		return GE;
	}

	@Override
	public Meta_data getData() {

		return new MetaData(this);
	}

	@Override
	public void translate(Point3D vec) {/////////////////////////////////////////////////////////
		MyCoords p = new MyCoords();

//		double lat = Double.parseDouble(this.lat);
//		double lon = Double.parseDouble(this.lon);
//		double alt = Double.parseDouble(this.alt);
//	pp=p.add( pp, vec);
		//		lat += vec.x();
		//		lon += vec.y();
		//		alt += vec.z();

	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}

}
package GIS;

import java.util.TimeZone;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;

import Geom.Point3D;

public class MetaData implements Meta_data {


	private    Calendar cal;
	private long UTC;
	private ZonedDateTime date ;
	private String Sdate;

	public MetaData(Element e) {
		cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.getTime();
		date = ZonedDateTime.now(ZoneOffset.UTC);
		Sdate = date.toLocalDate().toString();
	}
	public MetaData(Layer e) {
		cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.getTime();
		date = ZonedDateTime.now(ZoneOffset.UTC);
		Sdate = date.toLocalDate().toString();
	}
	public MetaData(Project e) {
		cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.getTime();
		date = ZonedDateTime.now(ZoneOffset.UTC);
		Sdate = date.toLocalDate().toString();
	}
	
	public String getDate() {
		return this.Sdate;
	}
	@Override
	public long getUTC() {
		UTC = cal.getTimeInMillis();
		return UTC;
	}
	public String toString() {
		return "Date - "+this.getDate()+" UTC - "+this.getUTC();
	}

	@Override
	public Point3D get_Orientation() {
		// TODO Auto-generated method stub/////////////
		return null;
	}

}

package Coords;

import java.util.Arrays;

import Geom.Point3D;

public class MyCoords implements coords_converter {
	public static void main(String[] args) {
		MyCoords m=new MyCoords();
		Point3D a = new Point3D("32.103315,35.209039,670");
		Point3D b = new Point3D("32.106352,35.205225,650");
		System.out.println(m.vector3D(a,b));
		System.out.println(m.distance3d(a,b));
		System.out.println(Arrays.toString(m.azimuth_elevation_dist(a, b)));
	}

	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

		return (new Point3D(
				gps.x() + Math.asin(local_vector_in_meter.x() / 6371000) * 180 / Math.PI 
				,gps.y() + Math.asin(local_vector_in_meter.y() / (Math.cos(gps.x() * Math.PI/180)) * 6371000) * 180 / Math.PI 
				,gps.z() + local_vector_in_meter.z())) ;
	}
	

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		Point3D v = vector3D(gps0, gps1);
		return (Math.sqrt(v.x()*v.x() + v.y()*v.y()));
	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double diffx = gps0.x()-gps1.x();
		double diffy = gps0.y()-gps1.y();
		double diffz = gps0.z()-gps1.z();
		// in radian
		diffx = diffx * Math.PI / 180;
		diffy = diffy * Math.PI / 180;
		// To meter
		diffx = Math.sin(diffx) * 6371000;
		diffy = Math.sin(diffy) * 6371000 * Math.cos(gps0.x() * Math.PI / 180);


		return (new Point3D(diffx, diffy, diffz));
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		////////////////////////////////////////////////////// to check //////////////////////////////////////////////////////////
		double azimuth = gps1.north_angle(gps0); 
		double elev = gps1.up_angle(gps0);
		double dist = distance3d(gps0 , gps1);
		double[] arr = {azimuth ,elev ,dist };
		return arr;
	}////////////////////////////////////////////////////TODO/////////////
	/**
	 * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {

		return (p.x() >= -180 && p.x() <= 180 && p.y() >= -90 && p.y() <= 90 && p.z() >= -450 );
	}

}

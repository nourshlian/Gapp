package Geom;

public class GeomElement implements Geom_element{

	public Point3D point;
	
	public GeomElement(Point3D p){
		
		this.point = p;
		
	}
	public double getX() {
		return this.point.x();
	}
	
	@Override
	public double distance3D(Point3D p) {
		
		return this.point.distance3D(p);
		
	}

	@Override
	public double distance2D(Point3D p) {
		
		return this.distance2D(p);
		
	}

}

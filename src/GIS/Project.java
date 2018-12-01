package GIS;

import java.util.ArrayList;

public class Project extends ArrayList<GIS_layer> implements GIS_project{

	private ArrayList<GIS_layer> elements;
	public Project() {
		ArrayList<GIS_layer> elements =new ArrayList<>();
		MetaData m = new MetaData(this) ;
	}

	
	
	@Override
	public Meta_data get_Meta_data() {

		return new MetaData(this);
	}

}

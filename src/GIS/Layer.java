package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Layer extends ArrayList<GIS_element> implements GIS_layer {


	private ArrayList<GIS_element> elements;
	public Layer() {
		ArrayList<GIS_element> elements =new ArrayList<>();
		MetaData m = new MetaData(this) ;
	}

	@Override
	public Meta_data get_Meta_data() {
		
		return new MetaData(this);
	}

}

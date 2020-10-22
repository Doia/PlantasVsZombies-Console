package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> builders;
	List<JSONObject> factoryElem;
	
	public BuilderBasedFactory(List<Builder<T>> bu)  {
		builders = new ArrayList<>(bu); 
		List<JSONObject> info = new ArrayList<>();
		for(Builder<T> b: builders) {
			info.add(b.getBuilderInfo());
		}
		factoryElem = Collections.unmodifiableList(info);
	}
	
	@Override
	public T createInstance(JSONObject info) {
		
		if(info == null) throw new IllegalArgumentException("JSONObject does not exist"); 
		
		for(Builder<T> bb: builders) {
			T o = bb.createInstance(info);
			if( o != null) {
				return o;
			}
		}
		throw new IllegalArgumentException(info.toString());
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> info = new ArrayList<>();
		for(Builder<T> b: builders) {
			info.add(b.getBuilderInfo());
		}
		return info;
	}
}

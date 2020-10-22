package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String type;
	protected String desc;
	
	public Builder(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	protected double[] jsonArrayTDoubleArray(JSONArray ja) {
		double da[] = new double[ja.length()];
		for(int i = 0; i < ja.length();i++) {
			da[i] = ja.getDouble(i);
		}
		return da;
	}
	
	protected abstract T createTheInstance(JSONObject jo);
	
	public  T createInstance(JSONObject info) {
		T o = null;
		if(this.type != null && type.equals(info.getString("type"))) {
			o = createTheInstance(info.getJSONObject("data"));
		}
		return o;
	}
	
	public  T createInstance(String[] data) {
		T o = null;
			//o = createData(data);
		return o;
	}
	
	public  JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", this.type);
		info.put("data", createData());
		info.put("desc", this.desc);
		
		return info;
	}
	
	protected  JSONObject createData() {
		return new JSONObject();
	}
}

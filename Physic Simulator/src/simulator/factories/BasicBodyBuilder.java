package simulator.factories;


import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	
	public static final String typeConst = "basic";
	public static final String descConst = "Basic Body";
	
	public BasicBodyBuilder() {
		super(typeConst, descConst);
	}
	
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("id", "id");
		o.put("pos", "vector pos");
		o.put("vel", "Vector vel");
		o.put("mass",  "mass");
		return o;
	}
	
	protected JSONObject createData(String[] data) {
		JSONObject o = null;
		if (data.length == 4) {
			o = new JSONObject();
			o.put("id", data[0]);
			o.put("pos", data[1]);
			o.put("vel", data[2]);
			o.put("mass",  data[3]);
		}
		return o;
	}

	@Override
	protected Body createTheInstance(JSONObject jo) {
		String id = jo.getString("id");
		Vector pos = new Vector(this.jsonArrayTDoubleArray(jo.getJSONArray("pos")));
		Vector a = new Vector(pos.dim());
		Vector vel = new Vector(this.jsonArrayTDoubleArray(jo.getJSONArray("vel")));
		Double mass = jo.getDouble("mass");
		
		return new Body(id, vel, a, pos, mass);
	}
}

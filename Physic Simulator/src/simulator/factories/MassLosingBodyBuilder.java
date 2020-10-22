package simulator.factories;

import java.util.ArrayList;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public static final String typeConst = "mlb";
	public static final String descConst = "Mass Lossing Body";
	
	public MassLosingBodyBuilder() {
		super(typeConst, descConst);
	}
	
	@Override
	protected JSONObject createData() {
	ArrayList<ArrayList<String>> hola;
		
		
		JSONObject o = new JSONObject();
		o.put("id", "id");
		o.put("pos", "vector pos");
		o.put("vel", "Vector vel");
		o.put("mass",  "mass");
		o.put("freq", "freq");
		o.put("factor", "factor");
		
		return o;
	}
	
	protected JSONObject createData(String[] data) {
		JSONObject o = null;
		if (data.length == 6) {
			o = new JSONObject();
			o.put("id", data[0]);
			o.put("pos", data[1]);
			o.put("vel", data[2]);
			o.put("mass",  data[3]);
			o.put("freq", data[4]);
			o.put("factor", data[5]);
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
		Double freq = jo.getDouble("freq");
		Double factor = jo.getDouble("factor");
		
		return new MassLossingBody(id, vel, a, pos, mass, freq, factor);
	}
	/*
	protected Body createTheInstance(String[] data) {
		String id = data[0];
		Vector pos = new Vector(this.jsonArrayTDoubleArray(jo.getJSONArray("pos")));
		Vector a = new Vector(pos.dim());
		Vector vel = new Vector(this.jsonArrayTDoubleArray(jo.getJSONArray("vel")));
		Double mass = jo.getDouble("mass");
		Double freq = jo.getDouble("freq");
		Double factor = jo.getDouble("factor");
		
		return new MassLossingBody(id, vel, a, pos, mass, freq, factor);
	}
	*/
}

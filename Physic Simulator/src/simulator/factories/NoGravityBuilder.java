package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {
	
	public static final String typeConst = "ng";
	public static final String descConst = "No gravity law";
	
	public NoGravityBuilder() {
		super(typeConst, descConst);
	}
	
	protected GravityLaws createTheInstance(JSONObject jo) {
		return new NoGravity();
	}
}

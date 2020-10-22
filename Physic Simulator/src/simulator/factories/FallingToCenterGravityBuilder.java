package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {
	
	public static final String typeConst = "ftcg";
	public static final String descConst = "Falling to center gravity law";
	
	public FallingToCenterGravityBuilder() {
		super(typeConst, descConst);
	}
	
	@Override
	protected GravityLaws createTheInstance(JSONObject jo) {
		return new FallingToCenterGravity();
	}
}

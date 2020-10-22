package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	public static final String typeConst = "nlug";
	public static final String descConst = "Newton's law of universal gravitation";
	
	public NewtonUniversalGravitationBuilder() {
		super(typeConst, descConst);
	}

	protected GravityLaws createTheInstance(JSONObject jo) {
		return new NewtonUniversalGravitation();
	}
}

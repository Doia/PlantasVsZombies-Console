package simulator.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator ps;
	private Factory<Body> fB;
	private Factory<GravityLaws> fG;
	
	public Controller(PhysicsSimulator p, Factory<Body> fb, Factory<GravityLaws> fg) {
		this.ps = p;
		this.fB = fb;
		this.fG = fg;
	}
	
	public void setDeltaTime(Double dt) {
		this.ps.setDeltaTime(dt);
	}
	
	public void setGravityLaws(JSONObject info) {
		this.ps.setGravityLaws(this.fG.createInstance(info));//maybe
	}

	public Factory<GravityLaws> getGravityLawsFactory() {
		return this.fG;
	}
	
	public Factory<Body> getBodiesFactory(){
		return this.fB;
	}
	
	public ArrayList<String> getIds_BodyList() {
		ArrayList<String> listId = ps.getBodyIds();
		return listId;
	}
	
	public boolean removeBody(String id) {
		return ps.removeBody(id);
	}
	
	public void callExceptions(String msg) {
		this.ps.exceptionCalled(msg);
	}
	
	public void reset() {
		this.ps.reset();
	}
	
	public void addObserver(SimulatorObserver o) {
		ps.addObserver(o);
	}
	
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			ps.advance();
		}
	}
	
	public void run ( int n , OutputStream o) throws IOException { 
		PrintStream p;
		
		if(o != null) {p = new PrintStream(o);}
		else {p = System.out;}
		
		String s ="{\n\"states\": [\n";
		s+= ps.toString() +", \n";
		for (int i = 0; i < n; i++) {
			ps.advance();
			s += ps.toString();
			if(i < n -1) {
				s += ", \n";
			}
		}
		s += " ]\n}";
		p.print(s);
	}
	
	public void loadBodies(InputStream in) {
		try {
			JSONObject jsonInput = new JSONObject(new JSONTokener(in));
			
			JSONArray bodies = jsonInput.getJSONArray("bodies");
			Body a;
			JSONObject b;
			
			for(int i = 0; i < bodies.length(); i++) {
				b = bodies.getJSONObject(i);
				a = fB.createInstance(b);
				ps.addBody(a);
			}
		}catch (JSONException e) {
			callExceptions("Incorrect file to load.");
		}
	}
	
	public void createBody(String[] data) {
		//fB.
	}
	
	public void addBody(Body b) {
		ps.addBody(b);
	}

	public void save(File out) {
try {
	
			String jsonOutput = "{ \"bodies\": [";
			jsonOutput += ps.outJSON();
		
			jsonOutput += "]}";
			
			
			PrintStream file = new PrintStream(out);
			
			file.print(jsonOutput);
			file.close();
			
		}catch (JSONException e) {
			callExceptions("Incorrect file to load.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			callExceptions("File not found");
		}
		
	}
}

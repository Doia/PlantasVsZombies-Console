package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private GravityLaws law;
	private List<Body> bodies;
	
	private List<SimulatorObserver> observers;
	
	private double time; //Tiempo actual
	private double dt; //Tiempo real por paso
	
	public PhysicsSimulator(GravityLaws g, double a) {
		this.bodies = new ArrayList<Body>();
		this.observers = new ArrayList<SimulatorObserver>();
		this.time = 0;
		this.dt = a;
		this.law = g;
	}
	
	public void setDeltaTime(double dt) {
		this.dt = dt;

		for(SimulatorObserver o: observers) {
			o.onDeltaTimeChanged(dt);
		}
	}
	
	public void setGravityLaws(GravityLaws gl) {
		this.law = gl;

		for(SimulatorObserver o: observers) {
			o.onGravityLawChanged(law.toString());
		}
	}
	
	public ArrayList<String> getBodyIds() {
		ArrayList<String> listId = new ArrayList<String>();
		for(Body body : bodies) {
			listId.add(body.id);
		}
		return listId;
	}
	
	
	public void addBody(Body b) {
		if(bodies.contains(b)) {
			for(SimulatorObserver o: observers) {
				o.onException("this body already exists");
			}
		}
		bodies.add(b);
		
		for(SimulatorObserver o: observers) {
			o.onBodyAdded(bodies, b);
		}
	}
	

	public boolean removeBody(String id) {
		boolean removed = false;
		int i = 0;
		while (i < bodies.size() && !removed) {
			if(id == bodies.get(i).getId()) {
				bodies.remove(i);
				removed = true;
			}
			i++;
		}
		for(SimulatorObserver o: observers) {
			o.onBodyRemoved(bodies);
		}
		return removed;

	}
	
	public void advance() {
		this.law.apply(bodies);
		
		for(Body b : bodies) {
			b.move(dt);
		}
		
		time += dt;

		for(SimulatorObserver o: observers) {
			o.onAdvance(bodies, time);
		}
	}
	
	public void reset() {
		this.bodies.clear();
		this.time = 0.0;
		
		for(SimulatorObserver o: observers) {
			o.onReset(bodies, time, dt, law.toString());
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!this.observers.contains(o)) {
			this.observers.add(o);
			o.onRegister(bodies, time, dt, law.toString());
		}
		
	}
	
	public void exceptionCalled(String msg) {
		for(SimulatorObserver o: observers) {
			o.onException(msg);
		}
	}
	
	public String toString() {
		String str = "{ \"time\": " + time + ", \"bodies\": [ ";
		
		for(int i = 0; i < bodies.size() - 1; i++) {
			str += bodies.get(i).toString() + ", ";
		}
		str += bodies.get(bodies.size() - 1).toString() + " ] }";
		
		return str;
	}

	public String outJSON() {
		String str = "";
		for(int i = 0; i < bodies.size(); i++) {
			str+= "{ \"type\": \"" + bodies.get(i).getType() + "\",";
			str += " \"data\" : " + bodies.get(i).toString() + "}, ";
		}
		return str;
	}




	
}
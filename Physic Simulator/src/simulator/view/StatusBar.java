package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {

	private JLabel _currTime; // For current time
	private JLabel _currLaws; // For gravity laws
	private JLabel _numOfBodies; // For number of bodies
	
	private double _time;
	private int _bodies;
	private String _law;

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		JToolBar tB = new JToolBar();
		this.add(tB);
		tB.addSeparator(new Dimension(10, 10));
		
		_currTime = new JLabel();
		tB.add(_currTime);
		tB.addSeparator(new Dimension(80, 10));
		
		_numOfBodies = new JLabel();
		tB.add(_numOfBodies);
		tB.addSeparator(new Dimension(80, 10));
		
		_currLaws = new JLabel();
		tB.add(_currLaws);
		
		updateLabelText();
	}

	//Update the value of the label 
	private void updateLabelText() {
		_currTime.setText("Time: " + _time);
		_currLaws.setText("Laws: " + _law);
		_numOfBodies.setText("Bodies: " + _bodies);
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_time = time;
				_law = gLawsDesc;
				updateLabelText();
			}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies.size();
				_time = time;
				_law = gLawsDesc;
				updateLabelText();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies.size();
				updateLabelText();
			}
		});
	}
	
	@Override
	public void onBodyRemoved(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies.size();
				updateLabelText();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies.size();
				_time = time;
				updateLabelText();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_law = gLawsDesc;
				updateLabelText();	
			}
		});
	}
	
	@Override
	public void onException(String exMsg) {}


}

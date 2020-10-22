package Launchers;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import GUI.MainWindow;
import Logic.Controller;


public class Main {

	public static void main(String[] args) {
		
		Controller ctrl = new Controller();
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run () {
					new MainWindow(ctrl); 
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

		
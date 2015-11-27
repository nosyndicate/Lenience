package multiagent;

import java.awt.Color;
import sim.display.Controller;
import sim.display.Display2D;
import sim.engine.SimState;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import mason.sim.display.GUIState;

public class CooperativeWithUI extends GUIState {

	public static void main(String[] args) {
        new CooperativeWithUI().createController();
    }

    public CooperativeWithUI() {
        super(new Cooperative(System.currentTimeMillis()));
    }

    public CooperativeWithUI(SimState state) {
        super(state);
    }

    // allow the user to inspect the model
    public Object getSimulationInspectedObject() {
        return state;
    }  
    
    public static String getName() {
        return "CooperativeGames";
    }


    public void start() {
        super.start(); 
    }

    public void load(SimState state) {
        super.load(state);
    }

    public void init(Controller c) {
        super.init(c);
    }

    public void quit() {
        super.quit();
    }

}

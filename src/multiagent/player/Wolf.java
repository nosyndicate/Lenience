package multiagent.player;

import multiagent.Player;
import sim.engine.SimState;
import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;

public class Wolf extends Player{

	public Wolf(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
	}
	
	@Override
	protected void reset(int state) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void processParameters(ParameterDatabase parameters) {
		
		
	}

	@Override
	protected void learning(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getAction(SimState sim) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int[] extractPolicy(SimState sim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printQTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printPolicy() {
		// TODO Auto-generated method stub
		
	}

	

}
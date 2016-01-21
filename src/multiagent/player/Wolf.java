package multiagent.player;

import multiagent.Player;
import sim.engine.SimState;
import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;

public class Wolf extends Player{

	public Wolf(ParameterDatabase parameters, int id, Game game) {
		super(id, game);
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

}
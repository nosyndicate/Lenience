package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;

public class FMQ extends Player{

	public FMQ(ParameterDatabase parameters, int id, Game game) {
		super(id, game);
	}

	@Override
	protected void learning(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getAction(Game game) {
		// TODO Auto-generated method stub
		return 0;
	}


}
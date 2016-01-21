package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;

public class DistributedQ extends Player {

	final private static double INITIAL_VALUE = 0;
	
	public DistributedQ(ParameterDatabase parameters, int id, Game game) {
		super(id, game);
		
		int stateNum = game.numStates;
		int actionNum = game.numActions[this.isFirstAgent()?0:1];
		
		this.initializeQValueTable(stateNum, actionNum, INITIAL_VALUE);
	}

	@Override
	protected void learning(Game game) {

	}

	@Override
	protected int getAction(Game game) {
		// TODO Auto-generated method stub
		return 0;
	}

}

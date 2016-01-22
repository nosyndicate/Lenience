package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;

public class DistributedQ extends Player {

	final private static double INITIAL_VALUE = 0;
	
	public DistributedQ(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
		
		int stateNum = game.numStates;
		int actionNum = game.numActions[this.isFirstAgent()?0:1];
		
		this.initializeQValueTable(stateNum, actionNum, INITIAL_VALUE);
	}

	@Override
	protected void learning(Game game) {

	}

	@Override
	protected int getAction(SimState sim) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void processParameters(ParameterDatabase parameters) {
		
		
	}

	@Override
	protected int[] extractPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		
	}

}

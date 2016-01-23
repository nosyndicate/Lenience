package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;

public class Lenience extends Player{
	
	private double[][] temperature;

	public Lenience(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
		
		int actionNum = game.numActions[this.isFirstAgent()?0:1];
		int stateNum = game.numStates;
		
		// TODO: need to deal with initialization value here
		int value = 0;
		double temperature = 50;
		
		this.initializeQValueTable(stateNum, actionNum, value);
		this.initializeTemperatureTable(stateNum, actionNum, temperature);
	}
	
	@Override
	protected void reset(int state) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	protected void processParameters(ParameterDatabase parameters) {
		
		
	}
	
	private void initializeTemperatureTable(int stateNum, int actionNum, double maxTemp) {
		this.temperature = new double[stateNum][actionNum];
		
		for(int i = 0;i<stateNum;++i)
		{
			for(int j = 0;j<actionNum;++j)
				this.temperature[i][j] = maxTemp;
		}
	}
	

	@Override
	protected void learning(Game game) {
		
		
	}

	@Override
	protected int getAction(SimState sim) {
		return 0;
	}



	@Override
	protected int[] extractPolicy() {
		// TODO Auto-generated method stub
		return null;
	}



	
}
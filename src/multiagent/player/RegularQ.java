package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import multiagent.Utils;
import sim.engine.SimState;
import ec.util.Parameter;

public class RegularQ extends Player {

	final private static double INITIAL_VALUE = 0;
	
	private double gamma;
	private double alpha;
	private double temperature;
	private double temperatureDecay;
	private double epsilon;
	private double epsilonDecay;

	public RegularQ(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
		numStates = game.numStates;

	}
	
	@Override
	protected void reset(int state) {
		this.initializeQValueTable(game.stateList, isFirstAgent(), INITIAL_VALUE);
		this.setState(state);
	}
	

	@Override
	protected void processParameters(ParameterDatabase parameters) {
		alpha = parameters.getDoubleWithDefault(new Parameter("alpha"), null, 0.1);
		gamma = parameters.getDoubleWithDefault(new Parameter("gamma"), null, 0.9);
		epsilon = parameters.getDoubleWithDefault(new Parameter("epsilon"), null, 0.1);
		epsilonDecay = parameters.getDoubleWithDefault(new Parameter("epsilondecay"), null, 1.0);
		temperature = parameters.getDoubleWithDefault(new Parameter("temperature"), null, 50);
		temperatureDecay = parameters.getDoubleWithDefault(new Parameter("temperaturedecay"), null, 0.999);
		int m = parameters.getIntWithDefault(new Parameter("mode"), null, 2);
		mode = ActionMode.values()[m];
	}
	
	@Override
	protected void learning(Game game) {
		double target = game.getReward(isFirstAgent());
		if (!game.isGameEnd())
			target += gamma * maxOf(qTable.get(game.currentState));

		qTable.get(state)[action] = (1 - alpha) * qTable.get(state)[action] + alpha
				* target;
		
		//s<-s'
		state = game.currentState;
	}

	@Override
	protected int getAction(SimState sim) {
		
		if(mode==ActionMode.TEMPERATURE) {
			action = Utils.boltzmannSelection(sim.random, temperature, qTable.get(state));
			temperature *= temperatureDecay;
		}
		else if(mode==ActionMode.EPSILONGREEDY) {
			action = Utils.epsilonGreedy(sim.random, epsilon, qTable.get(state));
		}
		else if(mode==ActionMode.DECREASEDEPSILONGREEDY) {
			action = Utils.epsilonGreedy(sim.random, epsilon, qTable.get(state));
			epsilon *= epsilon*epsilonDecay;
		}

		return this.action;
	}


	@Override
	protected int[] extractPolicy(SimState sim) {
		int[] policy = new int[numStates];
		for(int i = 0;i<numStates;++i)
		{
			int index = maxAt(qTable.get(i), sim.random);
			policy[i] = index;
		}
		
		return policy;
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
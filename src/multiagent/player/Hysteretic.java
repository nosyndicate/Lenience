package multiagent.player;



import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import multiagent.Utils;
import sim.engine.SimState;


public class Hysteretic extends Player {
	
	final private static double INITIAL_VALUE = 0;
	
	private double temperature = 0;
	private double temperatureDecay = 0;
	private double epsilon = 0;
	private double epsilonDecay = 0;
	private double gamma = 0.9;
	private double alpha = 0;
	private double beta = 0;

	public Hysteretic(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
		
		// TODO:need to initialize selection mode there
		
		int stateNum = game.numStates;
		
		state = game.currentState;
		
		this.initializeQValueTable(game.stateList, isFirstAgent(), INITIAL_VALUE);
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
		
		double rewardTerm = game.getReward(this.isFirstAgent());
		
		if(!game.isGameEnd()) {
			rewardTerm += gamma*maxOf(qTable.get(game.currentState));
		}
		
		double temporalDifference = rewardTerm - qTable.get(state)[action];
		
		if(temporalDifference >= 0) {
			qTable.get(state)[action] = qTable.get(state)[action] + alpha * temporalDifference;
		}
		else
			qTable.get(state)[action] = qTable.get(state)[action] + beta * temporalDifference;

		// s <- s'
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
		return action;
	}


	@Override
	protected int[] extractPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
}
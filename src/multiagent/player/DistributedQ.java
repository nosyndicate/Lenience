package multiagent.player;


import java.util.ArrayList;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import edu.gmu.cs.multiagent.matrix.State;
import multiagent.Player;
import multiagent.Utils;
import sim.engine.SimState;

public class DistributedQ extends Player {

	final private static double INITIAL_VALUE = 0;

	private double gamma;
	private double epsilon;
	private double epsilonDecay;

	
	private ArrayList<Double[]> policyTable;

	
	public DistributedQ(ParameterDatabase parameters, int id, Game game) {
		super(parameters, id, game);
		numStates = game.numStates;
	}
	
	@Override
	protected void reset(int state) {
		this.initializeQValueTable(game.stateList, isFirstAgent(), INITIAL_VALUE);
		this.initializePolicyTable(game.stateList, isFirstAgent());
		
		this.state = game.currentState;
		
	}
	
	private void initializePolicyTable(ArrayList<State> stateList, boolean firstAgent) {
		policyTable = new ArrayList<Double[]>();
		
		for(int i = 0;i<stateList.size();++i) {
			State s = stateList.get(i);
			
			int actionNum = firstAgent?s.numAgent1Action:s.numAgent2Action;
			double initValue = 1.0/(double)actionNum;
			
			Double[] values = new Double[actionNum];
			
			for(int j = 0;j<values.length;++j) {
				values[j] = initValue;
			}
			
			policyTable.add(values);
		}
	}

	@Override
	protected void learning(Game game) {
		Double[] qValue = qTable.get(state);
		
		double originalQ = maxOf(qValue);
		
		double rewardTerm = game.getReward(isFirstAgent());
		
		if(!game.isGameEnd())
			rewardTerm += gamma*maxOf(qTable.get(game.currentState));
		
		if(qValue[action]<rewardTerm)
			qValue[action] = rewardTerm;
		
		// we have a new policy, refresh the policy
		if(originalQ!=maxOf(qValue))
		{
			Double[] policy = policyTable.get(state);
			for(int i = 0;i<policy.length;++i) {
				if(i==action)
				{
					policy[i] = 1.0;
				}
				else
				{
					policy[i] = 0.0;
				}
			}
		}
		
		//s<- s'
		state = game.currentState;
	}

	@Override
	protected int getAction(SimState sim) {
		Double[] policy = policyTable.get(state);
		if(mode==ActionMode.EPSILONGREEDY){
			action = Utils.epsilonGreedy(sim.random, epsilon, policy);
		}
		else if(mode==ActionMode.DECREASEDEPSILONGREEDY){
			action = Utils.epsilonGreedy(sim.random, epsilon, policy);
			epsilon *= epsilon*epsilonDecay;
		}
		
		return action;
	}

	@Override
	protected void processParameters(ParameterDatabase parameters) {
		gamma = parameters.getDoubleWithDefault(new Parameter("gamma"), null, 0.9);
		epsilon = parameters.getDoubleWithDefault(new Parameter("epsilon"), null, 0.1);
		epsilonDecay = parameters.getDoubleWithDefault(new Parameter("epsilondecay"), null, 1.0);
		int m = parameters.getIntWithDefault(new Parameter("mode"), null, 2);
		mode = ActionMode.values()[m];
		
	}

	@Override
	protected int[] extractPolicy(SimState sim) {
		int[] policy = new int[numStates];
		for(int i = 0;i<numStates;++i)
		{
			int index = maxAt(policyTable.get(i),sim.random);
			policy[i] = index;
		}
		
		return policy;
	}

	public void printQTable(){
		System.out.println("QValues:");
		for(int i = 0;i<numStates;++i)
		{
			Double[] qValues = qTable.get(i);
			System.out.print("(");
			for(int j = 0;j<qValues.length;++j) {
				System.out.print(qValues[j]+" ");
			}
			System.out.print("\b)");
			System.out.println();
		}
	}
	
	public void printPolicy() {
		System.out.println("Policy:");
		for(int i = 0;i<numStates;++i)
		{
			Double[] policy = policyTable.get(i);
			System.out.print("(");
			for(int j = 0;j<policy.length;++j) {
				System.out.print(policy[j]+" ");
			}
			System.out.print("\b)");
			System.out.println();
		}
		
	}
	

}

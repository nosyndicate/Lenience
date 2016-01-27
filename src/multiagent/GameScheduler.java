package multiagent;

import edu.gmu.cs.multiagent.matrix.Game;
import sim.engine.SimState;
import sim.engine.Steppable;

public class GameScheduler implements Steppable {

	private static final long serialVersionUID = 1L;
	public Player firstPlayer;
	public Player secondPlayer;
	public double times;
	public double iterations;
	public int iterationCounter;
	public int timesCounter;
	public int[] results; // store the result of experiment, complete - 0, correct - 1, fail - 2
	
	
	public GameScheduler(Player firstPlayer, Player secondPlayer, double times, double iterations) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.times = times;
		this.iterations = iterations;
		this.iterationCounter = 0;
		this.timesCounter = 0;
		this.results = new int[3];
	}
	
	@Override
	public void step(SimState state) {
		
		
		
		Cooperative cooperative = (Cooperative) state;
		Game game = cooperative.game;
		
		Player firstPlayer = cooperative.playerOne;
		Player secondPlayer = cooperative.playerTwo;
		
		int action1 = firstPlayer.getAction(cooperative);
		int action2 = secondPlayer.getAction(cooperative);
		
		//System.out.println("current state is "+game.currentState);
		game.play(action1, action2);
		
		//System.out.println("action 1 is "+action1+", action two is "+action2);
		//System.out.println("reward is "+game.getReward(true)+","+game.getReward(false));
		//System.out.println("next state is "+game.currentState);
		
		firstPlayer.learning(game);
		secondPlayer.learning(game);
		
		iterationCounter++;
		if(iterationCounter == iterations)
		{
			//check policy if the learning phase is ended
			int[] policy1 = firstPlayer.extractPolicy(state);
			int[] policy2 = secondPlayer.extractPolicy(state);
			
			int type = game.checkPolicy(policy1, policy2);
			results[type]++;
			
			iterationCounter = 0;
			timesCounter++;
			
//			if(type==0)
//			{
//				firstPlayer.printQTable();
//				firstPlayer.printPolicy();
//				
//				secondPlayer.printQTable();
//				secondPlayer.printPolicy();
//			}
			
			
			
			
			if(timesCounter%1000==0)
				System.out.println("time:"+timesCounter);
			
			
			if(timesCounter == times)
			{
				// print the results
				System.out.printf("The result is %d, %d, %d%n", results[0], results[1], results[2]);
				
				// then stop the simulation
				Cooperative c = (Cooperative)state;
				c.stopper.stop();
			}
			
			// the experiement is not ended, reset the agents
			// reset the agents
			game.resetGame();
			firstPlayer.reset(game.currentState);
			secondPlayer.reset(game.currentState);
			
		}
		
		if(game.isGameEnd()) {
			game.resetGame();
			firstPlayer.setState(game.currentState);
			secondPlayer.setState(game.currentState);
		}
		
	}

}

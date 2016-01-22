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
	}
	
	@Override
	public void step(SimState state) {
		Cooperative cooperative = (Cooperative) state;
		Game game = cooperative.game;
		
		Player firstPlayer = cooperative.playerOne;
		Player secondPlayer = cooperative.playerTwo;
		
		game.play(firstPlayer.getAction(cooperative), secondPlayer.getAction(cooperative));
		
		firstPlayer.learning(game);
		secondPlayer.learning(game);
		
		iterationCounter++;
		if(iterationCounter == iterations)
		{
			//check policy if the learning phase is ended
			int[] policy1 = firstPlayer.extractPolicy();
			int[] policy2 = secondPlayer.extractPolicy();
			
			results[game.checkPolicy(policy1, policy2)]++;
			
			iterationCounter = 0;
			timesCounter++;
			
			if(timesCounter == times)
			{
				// print the results
				System.out.printf("The result is %d/%d, %d/%d, %d/%d%n", results[0], times, results[1], times, results[2], times);
				
				// then stop the simulation
				Cooperative c = (Cooperative)state;
				c.stopper.stop();
			}
			
			// the experiement is not ended, reset the agents
			// reset the agents
			firstPlayer.reset();
			secondPlayer.reset();
		}
		
		if(game.isGameEnd()) {
			game.resetGame();
		}
		
	}

}

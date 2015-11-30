package multiagent;

import edu.gmu.cs.multiagent.matrix.Game;
import sim.engine.SimState;
import sim.engine.Steppable;

public class GameScheduler implements Steppable {

	private static final long serialVersionUID = 1L;
	public Player firstPlayer;
	public Player secondPlayer;
	
	
	public GameScheduler(Player firstPlayer, Player secondPlayer) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
	}
	
	@Override
	public void step(SimState state) {
		Cooperative cooperative = (Cooperative) state;
		Game game = cooperative.game;
		
		Player firstPlayer = cooperative.playerOne;
		Player secondPlayer = cooperative.playerTwo;
		
		game.play(firstPlayer.getAction(), secondPlayer.getAction());
		
		firstPlayer.learning(game);
		secondPlayer.learning(game);
		
		if(game.isGameEnd()) {
			game.resetGame();
		}
	}

}

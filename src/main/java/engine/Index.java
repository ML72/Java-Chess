package engine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;
import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException {
		
		// Creates a new chessboard in the standard initial position
	    Board board = new Board();
	    
	    Minimax minimax = new Minimax(6, true, true);
	    Move move = minimax.findBestMove(board);
	    
	    System.out.println(move.toString());
	    System.out.println(minimax.NODES_EVALUATED + " nodes evaluated");
	    
	    //board.getPositionId();
	}

}

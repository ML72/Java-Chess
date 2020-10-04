package engine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;
import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException {
		
		// Creates a new chessboard in the standard initial position
	    Board board = new Board();
	    
	    Move move = Minimax.findBestMove(board, 4);
	    
	    System.out.println(move.toString());
	    System.out.println(Minimax.NODES_EVALUATED + " nodes evaluated");
	}

}

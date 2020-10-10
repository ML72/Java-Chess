package engine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

// for making the engine play full games with itself
public class Adversary {

	// file destination to write results to & Minimax engine
	public static String DESTINATION;
	private static Minimax MINIMAX;
	
	public Adversary(String destination, Minimax m) {
		
		DESTINATION = destination;
		MINIMAX = m;
	}
	
	public void playFullGame() throws MoveGeneratorException, FileNotFoundException {
		
		Board board = new Board();
		PrintWriter out = new PrintWriter(DESTINATION);
		
	    while(!board.isMated() && !board.isDraw()) {
	    	
		    Move move = MINIMAX.findBestMove(board);
		    try {
		    	
		    	// first line is the move, second is the # of nodes evaluated, the third is just an empty line for spacing
		    	out.println(move.toString());
		    	out.println(MINIMAX.NODES_EVALUATED);
		    	out.println();
			    board.doMove(move);
			    
		    } catch(Exception e) {
		    	
			    out.println("ENGINE ERROR");
			    break;
		    }
	    }
	    
	    out.close();
	}
}

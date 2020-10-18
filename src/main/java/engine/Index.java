package engine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException, FileNotFoundException {
		
		System.out.println("Starting adversary program");
		

		Board board = new Board();
		Minimax MINIMAX = new Minimax(4, true, true, true);
		
	    for(int i = 0; i < 5; i++) {
	    	
		    Move move = MINIMAX.findBestMove(board);
		    try {
		    	
		    	// first line is the move, second is the # of nodes evaluated, the third is just an empty line for spacing
		    	System.out.println(move.toString());
		    	System.out.println(MINIMAX.NODES_EVALUATED);
		    	System.out.println();
			    board.doMove(move);
			    
		    } catch(Exception e) {
		    	
			    System.out.println("ENGINE ERROR");
			    break;
		    }
	    }
		
		/*
		for(int i = 0; i < 200; i++) {
			
			int depth = i % 4 + 1;
			String dst = "C:\\Applications\\EE_Engines\\CustomResults\\Raw\\Game" + (i+1) + "_Depth" + depth + ".txt";
			Adversary a = new Adversary(dst, new Minimax(depth, true, true));
			a.playFullGame();
			
			System.out.println("Written game file to " + dst);
		}*/
		
	}

}

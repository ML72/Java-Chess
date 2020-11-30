package engine;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException, IOException, EncryptedDocumentException, InvalidFormatException {
		
		System.out.println("Starting adversary program");
		
		/*for(int i = 0; i < 198; i++) {
			
			int depth = i % 6 + 1;
			String dst = "C:\\Applications\\EE_Engines\\CustomResults\\FullOpt\\Output" + (i+1) + ".xlsx";
			Adversary a = new Adversary(dst, new Minimax(depth, true, true, true), "Iteration" + (i+1));
			a.playFullGame();
			
			System.out.println("Written game file to " + dst + " as iteration " + (i+1) + " with depth " + depth);
		}*/
		
		// rnbqkb1r/pppp1ppp/5n2/1B6/3QP3/8/PPP2PPP/RNB1K1NR b KQkq - 0 4
		Board board = new Board();
		Minimax m = new Minimax(6, true, true);
		board.loadFromFen("rnbqkb1r/pppp1ppp/5n2/1B6/3QP3/8/PPP2PPP/RNB1K1NR b KQkq - 0 4");
		//board.loadFromFen("rnbqkb1r/pppp1ppp/8/1B6/4Q3/8/PPP2PPP/RNB1K1NR b KQkq - 0 5");
		
		System.out.println(board.toString() + "\n\n");
		
		Move move = m.findBestMove(board);
		
		System.out.println(move.toString() + " - FINAL MOVE");
		System.out.println("nodes evaluated " + m.NODES_EVALUATED);
		
	}

}

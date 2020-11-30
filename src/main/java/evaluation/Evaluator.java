package evaluation;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.File;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Rank;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveList;

public class Evaluator {

	// A Quiescence Search to avoid the horizon effect
	public static int evaluateLeafNode(Board position, boolean maximizing) {
		
		// This engine prioritizes the Minimax search algorithm; the Quiescence search will be disabled for greater depths
		return evaluateLeafNodeWithDepth(position, maximizing, 0);
	}
	
	public static int evaluateLeafNodeWithDepth(Board position, boolean maximizing, int depth) {
		
		MoveList captures = MoveGenerator.generatePseudoLegalCaptures(position);
		
		// Test to see if there are any immediate captures
		if(captures.size() <= 0 || depth <= 0) {
			
			return quietEvaluation(position);
		} else {
			
			int optimal = maximizing ? -9000 : 9000;
			for(Move m : captures) {
				
				if(position.isMoveLegal(m, false)) {
					position.doMove(m);
					optimal = maximizing ? Math.max(optimal, evaluateLeafNodeWithDepth(position, !maximizing, depth - 1)) : Math.min(optimal, evaluateLeafNodeWithDepth(position, !maximizing, depth - 1));
					position.undoMove();
				}

			}
			
			return optimal;
		}
	}
	
	// Evaluation of a quiet position
	public static int quietEvaluation(Board position) {
		
		int valuation = 0;
		
		// loop through all ranks and files to get the piece
		for(byte rank = 0; rank < 8; rank++) {
			Rank r = Rank.allRanks[rank];
			for(byte file = 0; file < 8; file++) {
				File f = File.allFiles[file];
				
				if(!File.NONE.equals(f) && !Rank.NONE.equals(r)) {
					Square sq = Square.encode(r, f);
					Piece piece = position.getPiece(sq);
					
					valuation += PieceValues.pieceValue(rank, file, piece);
				} else {
					throw new RuntimeException("Leaf node evaluator can't decode ranks and files");
				}

			}
		}
		
		return valuation;
	}
}

package evaluation;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class PieceValues {

	protected static int pieceValue(byte rank, byte file, Piece piece) {
		
		// return the position values encoded in the position arrays; based roughly on scale p=1, n=3, b=3, r=5, q=9
		if(Piece.NONE.equals(piece)) {
			return 0;
		} else if(Piece.WHITE_PAWN.equals(piece)) {
			return PAWN[7-rank][file];
		} else if(Piece.BLACK_PAWN.equals(piece)) {
			return -PAWN[rank][file];
		} else if(Piece.WHITE_KNIGHT.equals(piece)) {
			return KNIGHT[7-rank][file];
		} else if(Piece.WHITE_BISHOP.equals(piece)) {
			return BISHOP[7-rank][file];
		} else if(Piece.WHITE_ROOK.equals(piece)) {
			return ROOK[7-rank][file];
		} else if(Piece.BLACK_KNIGHT.equals(piece)) {
			return -KNIGHT[rank][file];
		} else if(Piece.BLACK_BISHOP.equals(piece)) {
			return -BISHOP[rank][file];
		} else if(Piece.BLACK_ROOK.equals(piece)) {
			return -ROOK[rank][file];
		} else if(Piece.WHITE_QUEEN.equals(piece)) {
			return QUEEN[7-rank][file];
		} else if(Piece.WHITE_KING.equals(piece)) {
			return KING[7-rank][file];
		} else if(Piece.BLACK_QUEEN.equals(piece)) {
			return -QUEEN[rank][file];
		} else if(Piece.BLACK_KING.equals(piece)) {
			return -KING[rank][file];
		}
				
		throw new RuntimeException("Invalid piece value");
	}
	
	// passed pawns on seventh rank are good, pawns that control center are good
	private static short[][] PAWN = {
			{100, 100, 100, 100, 100, 100, 100, 100},
			{150, 150, 150, 150, 150, 150, 150, 150},
			{110, 110, 120, 130, 130, 120, 110, 110},
			{105, 105, 110, 125, 125, 110, 105, 105},
			{100, 100, 100, 120, 120, 100, 100, 100},
			{105, 95,  90,  100, 100, 90,  95,  105},
			{105, 110, 110, 80,  80,  110, 110, 105},
			{100, 100, 100, 100, 100, 100, 100, 100}
	};
	
	// knights as outposts are good, in order is bad
	private static short[][] KNIGHT = {
			{250, 260, 270, 270, 270, 270, 260, 250},
			{260, 280, 300, 300, 300, 300, 280, 260},
			{270, 300, 310, 315, 315, 310, 300, 270},
			{270, 305, 315, 320, 320, 315, 305, 270},
			{270, 300, 315, 320, 320, 315, 300, 270},
			{270, 305, 310, 315, 315, 310, 305, 270},
			{260, 280, 300, 305, 305, 300, 280, 260},
			{250, 260, 270, 270, 270, 270, 260, 250}
	};
	
	// bishops are good when in the center, where they get diagonal control and maneuverability
	private static short[][] BISHOP = {
			{280, 290, 290, 290, 290, 290, 290, 280},
			{290, 300, 300, 300, 300, 300, 300, 290},
			{290, 300, 305, 310, 310, 305, 300, 290},
			{290, 305, 305, 310, 310, 305, 305, 290},
			{290, 310, 310, 310, 310, 310, 310, 290},
			{290, 305, 300, 300, 300, 300, 305, 290},
			{290, 305, 300, 300, 300, 300, 305, 290},
			{280, 290, 290, 290, 290, 290, 290, 280}
	};
	
	// rooks on 7th are good, on edge they're bad
	private static short[][] ROOK = {
			{500, 500, 500, 500, 500, 500, 500, 500},
			{505, 510, 510, 510, 510, 510, 510, 505},
			{495, 500, 500, 500, 500, 500, 500, 495},
			{495, 500, 500, 500, 500, 500, 500, 495},
			{495, 500, 500, 500, 500, 500, 500, 495},
			{495, 500, 500, 500, 500, 500, 500, 495},
			{495, 500, 500, 500, 500, 500, 500, 495},
			{500, 500, 500, 505, 505, 500, 500, 500}
	};
	
	// queens in the center where they can support and attack are good, note A4 and C2 squares
	private static short[][] QUEEN = {
			{880, 890, 890, 895, 895, 890, 890, 880},
			{890, 900, 900, 900, 900, 900, 900, 890},
			{890, 900, 905, 905, 905, 905, 900, 890},
			{895, 900, 905, 905, 905, 905, 900, 895},
			{900, 900, 905, 905, 905, 905, 900, 895},
			{890, 905, 905, 905, 905, 905, 900, 890},
			{890, 900, 905, 900, 900, 900, 900, 890},
			{880, 890, 890, 895, 895, 890, 890, 880}
	};
	
	// Both sides will have a king, so these are simply positioning values - kings on the other side are bad, castled kings are good
	private static short[][] KING = {
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-20, -30, -30, -40, -40, -30, -30, -20},
			{-10, -20, -20, -20, -20, -20, -20, -10},
			{20,  20,  0,   0,   0,   0,   20,  20 },
			{20,  30,  10,  0,   0,   10,  30,  20 }
	};
}

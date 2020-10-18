package engine;

import java.io.FileNotFoundException;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

// for making the engine play full games with itself
public class Adversary {

	// file destination to write results to - excel file
	public static String DESTINATION;
	public static String SHEET_NAME;
	private static Minimax MINIMAX;
	
	public Adversary(String destination, Minimax m) {
		
		this(destination, m, "Results");
	}
	
	public Adversary(String destination, Minimax m, String sheetName) {
		
		DESTINATION = destination;
		MINIMAX = m;
		SHEET_NAME = sheetName;
	}
	
	public void playFullGame() throws MoveGeneratorException, IOException {
		
		// excel sheet output settings
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    FileOutputStream out = new FileOutputStream(new File(DESTINATION));
	    XSSFSheet sheet = workbook.createSheet(SHEET_NAME);
	    
	    // create headings
	    Row headingRow = sheet.createRow(0);
	    headingRow.createCell(0).setCellValue("Move");
	    headingRow.createCell(1).setCellValue("Nodes Evaluated");
	    headingRow.createCell(2).setCellValue("Alpha-Beta Cutoffs");
	    headingRow.createCell(3).setCellValue("Transposition Cutoffs");
	    headingRow.createCell(4).setCellValue("Re-searches");
	    
	    Row averageRow = sheet.createRow(1);
	    averageRow.createCell(0).setCellValue("AVERAGE");
	    Cell c = averageRow.createCell(1);
	    c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    c.setCellFormula("AVERAGE(B3:B1000)");
	    c = averageRow.createCell(2);
	    c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    c.setCellFormula("AVERAGE(C3:C1000)");
	    c = averageRow.createCell(3);
	    c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    c.setCellFormula("AVERAGE(D3:D1000)");
	    c = averageRow.createCell(4);
	    c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    c.setCellFormula("AVERAGE(E3:E1000)");
	    
	    int rowCount = 1;
	    
	    // set up chess game and write moves and data to workbook
		Board board = new Board();
		
	    while(!board.isMated() && !board.isDraw()) {
	    	
		    Row dataRow = sheet.createRow(++rowCount);

		    Move move = MINIMAX.findBestMove(board);
		    try {
		    	
		    	System.out.println(move.toString() + " - move " + (rowCount - 1));
		    	
		    	dataRow.createCell(0).setCellValue(move.toString());
		    	dataRow.createCell(1).setCellValue(MINIMAX.NODES_EVALUATED);
		    	dataRow.createCell(2).setCellValue(MINIMAX.ALPHA_BETA_CUTOFFS);
		    	dataRow.createCell(3).setCellValue(MINIMAX.IDENTICAL_TRANSPOSITIONS);
		    	dataRow.createCell(4).setCellValue(MINIMAX.RE_SEARCHES);

			    board.doMove(move);
			    
		    } catch(Exception e) {
		    	
		    	dataRow.createCell(0).setCellValue("ENGINE ERROR");
			    System.err.println("ENGINE ERROR");
			    break;
		    }
	    }
	    
		// close resources
		workbook.write(out);
		workbook.close();
		out.close();
	
	}
}

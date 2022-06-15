// 
public class BlokTest2{
	//Properties
	int playerNum;
	int column;
	int row;
	int rotation;
	int intCount;
	int intCount2;
	int intBoard[][] = new int[5][5];
	int tempBoard[][] = new int[5][5];
	
	
	//Methods
	public void Read_ResetBoard(){

	}
	public int[][] PickPiece(int pieceType){
		for(intCount = 0;intCount < 5;intCount++){
			for(intCount2 = 0;intCount2 < 5; intCount2++){
				intBoard[intCount][intCount2] = 0;
			}
		}
		
		//One 1-square piece
		if (pieceType == 1){
			intBoard[2][2] = 1;
			
		}
		
		//One 2-square piece
		else if(pieceType == 2){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
		}
		
		//Two 3-square pieces
		else if(pieceType == 3){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
		}else if(pieceType == 4){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[3][1] = 1;
		}
		
		//Five 4-square pieces
		else if(pieceType == 5){
			intBoard[0][2] = 1;
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
		}else if(pieceType == 6){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[3][3] = 1;
		}else if(pieceType == 7){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[2][1] = 1;
		}else if(pieceType == 8){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[2][1] = 1;
			intBoard[3][1] = 1;
		}else if(pieceType == 9){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[2][3] = 1;
			intBoard[3][1] = 1;
		}
		
		//Twelve 5-square pieces
		else if(pieceType == 10){
			intBoard[0][2] = 1;
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[4][2] = 1;
		}else if(pieceType == 11){
			intBoard[0][2] = 1;
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[3][3] = 1;
		}else if(pieceType == 12){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[3][1] = 1;
			intBoard[3][0] = 1;
			intBoard[2][3] = 1;
		}else if(pieceType == 13){
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[2][1] = 1;
			intBoard[3][1] = 1;
			intBoard[3][0] = 1;
		}else if(pieceType == 14){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[3][1] = 1;
			intBoard[3][0] = 1;
		}else if(pieceType == 15){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[1][3] = 1;
			intBoard[2][1] = 1;
			intBoard[3][1] = 1;
		}else if(pieceType == 16){
			intBoard[2][1] = 1;
			intBoard[2][2] = 1;
			intBoard[2][3] = 1;
			intBoard[1][3] = 1;
			intBoard[3][1] = 1;
		}else if(pieceType == 17){
			intBoard[2][1] = 1;
			intBoard[2][2] = 1;
			intBoard[2][3] = 1;
			intBoard[3][1] = 1;
			intBoard[3][3] = 1;
		}else if(pieceType == 18){
			intBoard[1][2] = 1;
			intBoard[2][2] = 1;
			intBoard[3][2] = 1;
			intBoard[4][2] = 1;
			intBoard[2][1] = 1;
		}else if(pieceType == 19){
			intBoard[2][1] = 1;
			intBoard[2][2] = 1;
			intBoard[2][3] = 1;
			intBoard[1][2] = 1;
			intBoard[3][2] = 1;
		}else if(pieceType == 20){
			intBoard[2][1] = 1;
			intBoard[2][2] = 1;
			intBoard[2][3] = 1;
			intBoard[3][2] = 1;
			intBoard[1][3] = 1;
		}else if(pieceType == 21){
			intBoard[2][1] = 1;
			intBoard[2][2] = 1;
			intBoard[2][3] = 1;
			intBoard[0][2] = 1;
			intBoard[1][2] = 1;
		}
		return intBoard;
	}
	public int[][] rotatePiece(int intBoard[][]){
		for(intCount = 0;intCount < 5;intCount++){
			for(intCount2 = 0;intCount2 < 5; intCount2++){
				tempBoard[intCount][intCount2] = 0;
			}
		}
		// Rotate Piece
		/*  1 2 3 4 5
		 1* 0 0 0 0 0
		 2* 0 0 0 0 0 
		 3* 0 0 0 0 0
		 4* 0 0 0 0 0
		 5* 0 0 0 0 0
		 */
		 
		//rotate loop
		for(intCount2 = 0;intCount2 < 5;intCount2++){
			for(intCount = 0;intCount < 5;intCount++){
				tempBoard[intCount][intCount2] = intBoard[intCount2][4-intCount];
			}
		}
		for(intCount = 0;intCount < 5;intCount++){
			for(intCount2 = 0;intCount2 < 5; intCount2++){
				intBoard[intCount][intCount2] = tempBoard[intCount][intCount2];
			}
		}

		return intBoard;
		
		
	}
	
	
	//Constructor
	public BlokTest2(){
	}
}

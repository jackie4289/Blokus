public class Block{
	//Properties
	int intBoard[][] = new int[20][20];
		// 0 = EMPTY
		// 1 = RED (P1)
		// 2 = BLUE (P2)
	//Methods
	public void Read_ResetBoard(){
		int intRow;
		int intCol;
		for(intRow = 0; intRow < 20; intRow++){
			for(intCol = 0; intCol < 20; intCol++){
				intBoard[intRow][intCol] = 0;
			}
		}
	}
	//Constructor
	public Block(){
	}  
}

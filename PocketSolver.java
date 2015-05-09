import java.util.*;

public class PocketSolver {

	static int[] cubiesPos = new int[8];
	static int[] cubiesRot = new int[8];
	static int movecount = 0;
	static String moveList = "";

	public static void main(String[] args) {

		//--------------START INPUT-------------------//

		Scanner scannerInput = new Scanner(System.in);

		char[] input = new char[24];
		for (int x = 0; x < 24; x++) {
			input[x] = scannerInput.next().charAt(0);
		}
		
		scannerInput.close();

		long startTime = System.nanoTime();

		//----------------END INPUT--------------------//

		//---------------START INITIALIZATION-----------//

		char[][] cubies = new char[8][3];

		cubies[0] = new char[]{input[0],	input[16],	input[14]};
		cubies[1] = new char[]{input[1],	input[15],	input[21]};
		cubies[2] = new char[]{input[2],	input[4],	input[17]};
		cubies[3] = new char[]{input[3],	input[20],	input[5]};
		cubies[4] = new char[]{input[10],	input[12],	input[18]};
		cubies[5] = new char[]{input[11],	input[23],	input[13]};
		cubies[6] = new char[]{input[8],	input[19],	input[6]};
		cubies[7] = new char[]{input[9],	input[7],	input[22]};

		char[][] cubieColors = new char[][]{
			{'y', 'r', 'b'}, {'r', 'b', 'y'}, {'b', 'y', 'r'},
			{'y', 'b', 'o'}, {'b', 'o', 'y'}, {'o', 'y', 'b'},
			{'y', 'g', 'r'}, {'g', 'r', 'y'}, {'r', 'y', 'g'},
			{'y', 'o', 'g'}, {'o', 'g', 'y'}, {'g', 'y', 'o'},
			{'w', 'b', 'r'}, {'b', 'r', 'w'}, {'r', 'w', 'b'},
			{'w', 'o', 'b'}, {'o', 'b', 'w'}, {'b', 'w', 'o'},
			{'w', 'r', 'g'}, {'r', 'g', 'w'}, {'g', 'w', 'r'},
			{'w', 'g', 'o'}, {'g', 'o', 'w'}, {'o', 'w', 'g'}
		};

		for (int x = 0; x < 8; x++){
			int index = 0;
			while (true){
				if (Arrays.equals(cubies[x], cubieColors[index])) {
					break;
				}
				index++;
			}
			cubiesPos[x] = index / 3;
			cubiesRot[x] = index % 3;
		}

		//---------------END INITIALIZATION--------------//

		//--------------START FIRST LAYER----------------//

		if (!(cubiesPos[6] == 6)){ //Moving cubie 6 into position
			int sixpos = findCubie(6);
			if (sixpos > 3){ //if it's on the bottom
				rotateFaces(new String[]{"R", "R"}); //Now it must be on the top
				sixpos = findCubie(6);
			}
			while (cubiesPos[2] != 6){ //rotate into correct top position
				rotateFaces(new String[]{"T"});
			}
			rotateFaces(new String[]{"F'"});
		}

		if (!(cubiesPos[7] == 7)){ //Moving cubie 7 into position
			if (cubiesPos[5] == 7){ //If it's on the bottom in the other corner
				rotateFaces(new String[]{"R"});
			}
			else { //it must be on top
				while (cubiesPos[3] != 7){ //rotate top until we can flip it down into position
					rotateFaces(new String[]{"T"});
				}
				rotateFaces(new String[]{"R'"});
			}
		}

		if (cubiesPos[5] != 5){ //Moving cubie 5 into position
			while (cubiesPos[3] != 5){ //Must be on top, so rotate top into position
				rotateFaces(new String[]{"T"});
			}
			rotateFaces(new String[]{"F", "R'", "F'"});  //Slide it in
		}

		//Cubies are now in position, start algorithm that rotates without disturbing first level cubies

		while (cubiesRot[5] != 0 || cubiesPos[5] != 5){
			rotateFaces(new String []{"F", "R", "F'", "T'"});
		}

		while (cubiesRot[7] != 0 || cubiesPos[7] != 7){
			rotateFaces(new String []{"R", "T", "R'", "T'"});
		}

		while (cubiesRot[6] != 0 || cubiesPos[6] != 6){
			rotateFaces(new String []{"F", "T", "F'", "T'"});
		}

		//First layer cubies should be in both right position and orientation

		//------------END FIRST LAYER---------------//

		//------------START SECOND LAYER--------------//

		while (cubiesPos[1] != 1){ //get cubie 1 into position
			rotateFaces(new String[]{"T"});
		}

		//now get all cubies into position
		if (cubiesPos[0] != 0 && cubiesPos[2] != 2 && cubiesPos[3] != 3){ //All different
			while (cubiesPos[0] != 0){
				rotateFaces(new String[]{"T", "T", "R", "T'", "R'", "F", "R'", "F'", "R", "T'"});
			}
		}

		else if (cubiesPos[0] != 0 && cubiesPos[3] != 3){ //Two corners are swapped, so switch them
			rotateFaces(new String[]{"F", "R", "T", "R'", "T'", "F'", "T"});
		}

		else if (cubiesPos[0] != 0 && cubiesPos[2] != 2){ //Left half is swapped
			rotateFaces(new String[]{"T'", "R", "T'", "R'", "T'", "F'", "T", "F", "T"});
		}

		else if (cubiesPos[2] != 2 && cubiesPos[3] != 3) { //Close half is swapped, same algorithm as the last with the top moves at beginning and end
			rotateFaces(new String[]{"R", "T'", "R'", "T'", "F'", "T", "F"});
		}

		//Should all be in position

		rotateFaces(new String[]{"T"});

		for (int x = 0; x < 4; x++){
			int thisCubie;
			if (x == 2){
				thisCubie = 3;
			}
			else if (x == 3) {
				thisCubie = 2;
			}
			else {
				thisCubie = x;
			}

			while (!(cubiesRot[1] == 0 && cubiesPos[1] == thisCubie)){
				rotateFaces(new String[]{"R", "T", "F'", "T'"});
			}
			rotateFaces(new String[]{"T'"});
		}

		while (cubiesPos[0] != 0){
			rotateFaces(new String[]{"T"});
		}

		//------------END SECOND LAYER----------------//

		long stopTime = (System.nanoTime() - startTime)/1000;
		System.out.println(moveList);
		System.out.println(movecount);
		System.out.println(stopTime + " micrcoseconds");

	}

	public static void rotateFaces(String[] moves){ // Accepts an array of strings where each string corresponds to one move in the following switch statement
		for (String face : moves){
			movecount++;
			switch (face) {
				case "F":
					cubiesPos = new int[]{cubiesPos[0], cubiesPos[1], cubiesPos[6], cubiesPos[2], cubiesPos[4], cubiesPos[5], cubiesPos[7], cubiesPos[3]};
					cubiesRot = new int[]{cubiesRot[0], cubiesRot[1], (cubiesRot[6]+1) % 3, (cubiesRot[2]+2) % 3, cubiesRot[4], cubiesRot[5], (cubiesRot[7]+2) % 3, (cubiesRot[3]+1) % 3};
					moveList += "F ";
					break;
				case "R":
					cubiesPos = new int[]{cubiesPos[0], cubiesPos[3], cubiesPos[2], cubiesPos[7], cubiesPos[4], cubiesPos[1], cubiesPos[6], cubiesPos[5]};
					cubiesRot = new int[]{cubiesRot[0], (cubiesRot[3]+2) % 3, cubiesRot[2], (cubiesRot[7]+1) % 3, cubiesRot[4], (cubiesRot[1]+1) % 3, cubiesRot[6], (cubiesRot[5]+2) % 3};
					moveList += "R ";
					break;
				case "T":
					cubiesPos = new int[]{cubiesPos[2], cubiesPos[0], cubiesPos[3], cubiesPos[1], cubiesPos[4], cubiesPos[5], cubiesPos[6], cubiesPos[7]};
					cubiesRot = new int[]{cubiesRot[2], cubiesRot[0], cubiesRot[3], cubiesRot[1], cubiesRot[4], cubiesRot[5], cubiesRot[6], cubiesRot[7]};
					moveList += "T ";
					break;
				case "F'":
					cubiesPos = new int[]{cubiesPos[0], cubiesPos[1], cubiesPos[3], cubiesPos[7], cubiesPos[4], cubiesPos[5], cubiesPos[2], cubiesPos[6]};
					cubiesRot = new int[]{cubiesRot[0], cubiesRot[1], (cubiesRot[3]+1) % 3, (cubiesRot[7]+2) % 3, cubiesRot[4], cubiesRot[5], (cubiesRot[2]+2) % 3, (cubiesRot[6]+1) % 3};
					moveList += "F' ";
					break;
				case "R'":
					cubiesPos = new int[]{cubiesPos[0], cubiesPos[5], cubiesPos[2], cubiesPos[1], cubiesPos[4], cubiesPos[7], cubiesPos[6], cubiesPos[3]};
					cubiesRot = new int[]{cubiesRot[0], (cubiesRot[5]+2) % 3, cubiesRot[2], (cubiesRot[1]+1) % 3, cubiesRot[4], (cubiesRot[7]+1) % 3, cubiesRot[6], (cubiesRot[3]+2) % 3};
					moveList += "R' ";
					break;
				case "T'":
					cubiesPos = new int[]{cubiesPos[1], cubiesPos[3], cubiesPos[0], cubiesPos[2], cubiesPos[4], cubiesPos[5], cubiesPos[6], cubiesPos[7]};
					cubiesRot = new int[]{cubiesRot[1], cubiesRot[3], cubiesRot[0], cubiesRot[2], cubiesRot[4], cubiesRot[5], cubiesRot[6], cubiesRot[7]};
					moveList += "T' ";
					break;
				default:
					break;
			}
		}
	}

	public static int findCubie(int cubie){
		int index = 0;
		while (true){
			if (cubiesPos[index] == cubie){
				break;
			}
			index++;
		}
		return index;
	}
}

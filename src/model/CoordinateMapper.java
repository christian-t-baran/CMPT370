package model;

import containers.Pair;

/**
 * This class models a means to map interpreter range+thetas to actual coordinates on the hex grid
 */
public class CoordinateMapper {

	// Store number of teams when CoordinateMapper is constructed
	private final int numTeams;
	
	/**
	 * Constructor CoordinateMapper
	 * @param i
	 */
	public CoordinateMapper(int i){
		numTeams = i;
	}
	
	/**
	 * Maps a set of x, y coordinates with a range, theta, and team number to the corresponding
	 * new x, y coordinates
	 * @param xOrigin
	 * @param yOrigin
	 * @param range
	 * @param theta
	 * @param teamNum
	 * @return
	 */
	public Pair<Integer, Integer> getCoords(int xOrigin, int yOrigin, int range, 
			int theta, int teamNum){
		Pair<Integer, Integer> retPair = null;
		
		
		// just return the Robot's origin coordinates if range is 0
		if (range == 0){
			retPair = new Pair<Integer, Integer>(xOrigin, yOrigin);
		}
		// else calculate the new coordinates
		else if (range <= 3){
			retPair = mapRange(xOrigin, yOrigin, range, theta, teamNum);
		}
		
		return retPair;
		
		
	}
	
	/**
	 * Returns coordinates obtained by visiting the theta-neighbour of xOrigin, yOrigins
	 * @param xOrigin
	 * @param yOrigin
	 * @param theta
	 * @return
	 */
	public static Pair<Integer, Integer> extendPath(int xOrigin, int yOrigin, int theta){
		return mapRange1(xOrigin, yOrigin, theta);
	}
	
	/**
	 * Adjusts theta based on team number and range
	 * @param teamNum
	 * @param range
	 * @param theta
	 * @return
	 */
	private int getAdjustedTheta(int teamNum, int range, int theta){
		int adjTheta = 0;
		
		// adjust theta depending on number of teams and team number
		if( numTeams == 2){
			adjTheta = ( theta + ( (3 * range) * (teamNum - 1) ) ) % (range * 6);
		}
		else if ( numTeams == 3){
			adjTheta = ( theta + ( (2 * range) * (teamNum - 1) ) ) % (range * 6);
		}
		else if ( numTeams == 6){
			adjTheta = ( theta + ( range * (teamNum - 1) ) ) % (range * 6);
		}
		else{
			// Add in Exception handling
		}
		
		return adjTheta;
	}
	
	/**
	 * Maps transition from xOrigin, yOrigin to new coordinates along a range, theta, and teamNum
	 * @param xOrigin
	 * @param yOrigin
	 * @param range
	 * @param theta
	 * @param teamNum
	 * @return
	 */
	private Pair<Integer, Integer> mapRange(int xOrigin, int yOrigin, int range, int theta, int teamNum){
		// adjust Theta based on Team Number
		if( teamNum != 1){
			theta = getAdjustedTheta(teamNum, range, theta); 
		}	
		
		Pair<Integer, Integer> returnPair = null;
		
		// apply appropriate mapping formula based on range
		switch(range){
			case 1:	returnPair = mapRange1(xOrigin, yOrigin, theta);
					break;
			case 2: returnPair = mapRange2(xOrigin, yOrigin, theta);
					break;
			case 3: returnPair = mapRange3(xOrigin, yOrigin, theta);
					break;
			default: break; 
		}
		
		return returnPair;
	}
	
	/**
	 * Function to transition of range on theta from xOrigin, yOrigin 
	 * @param xOrigin
	 * @param yOrigin
	 * @param range
	 * @param theta
	 * @return
	 */
	private static Pair<Integer, Integer> mapRange1(int xOrigin, int yOrigin, int theta){
		int newX = xOrigin;
		int newY = yOrigin;
		
		// Switch & get offset based on theta
		switch(theta){
			case 0: newX += 1;
					break;
			case 1: newY += 1;
					break;
			case 2:	newX += -1;
					newY += 1;
					break;
			case 3: newX += -1;
					break;
			case 4: newY += -1;
					break;
			case 5:	newX += 1;
					newY += -1;
					break;			
			default:	
					break;
		}
		
		return new Pair<Integer, Integer>(newX, newY);
	}
	
	
	/**
	 * Maps xOrigin, yOrigin, and theta to new coordinates at a range 3 transition
	 * @param xOrigin
	 * @param yOrigin
	 * @param theta
	 * @return
	 */
	private Pair<Integer, Integer> mapRange2(int xOrigin, int yOrigin, int theta){
		int newX = xOrigin;
		int newY = yOrigin;
		
		// Switch & get offset based on theta
		switch(theta){
			case 0: 	newX += 2;
						break;
			case 1:		newX += 1;
						newY += 1;
						break;
			case 2:		newY += 2;
						break;
			case 3: 	newX += -1;
						newY += 2;
						break;
			case 4: 	newX += -2;
						newY += 2;
						break;
			case 5:		newX += -2;
						newY += 1;
						break;			
			case 6: 	newX += -2;
						break;
			case 7: 	newX += -1;
						newY += -1;
						break;
			case 8: 	newY += -2;
						break;
			case 9: 	newX += 1;
						newY += -2;
						break;
			case 10: 	newX += 2;
						newY += -2;
						break;
			case 11: 	newX += 2;
						newY += -1;
						break;
			default:	
					break;
		}
		
		return new Pair<Integer, Integer>(newX, newY);
	}

	/**
	 * Maps xOrigin, yOrigin, and theta to new coordinates at a range 3 transition
	 * @param xOrigin
	 * @param yOrigin
	 * @param theta
	 * @return
	 */
	private Pair<Integer, Integer> mapRange3(int xOrigin, int yOrigin, int theta){
		int newX = xOrigin;
		int newY = yOrigin;
		
		// Switch & get offset based on theta
		switch(theta){
			case 0: 	newX += 3;
						break;
			case 1:		newX += 2;
						newY += 1;
						break;				
			case 2:		newX += 1;
						newY += 2;
						break;
			case 3: 	newY += 3;
						break;
			case 4: 	newX += -1;
						newY += 3;
						break;
			case 5:		newX += -2;
						newY += 3;
						break;			
			case 6: 	newX += -3;
						newY += 3;
						break;
			case 7: 	newX += -3;
						newY += 2;
						break;
			case 8: 	newX += -3;
						newY += 1;
						break;
			case 9: 	newX += -3;
						break;
			case 10: 	newX += -2;
						newY += -1;
						break;
			case 11: 	newX += -1;
						newY += -2;
						break;
			case 12:	newY += -3;
						break;
			case 13:	newX += 1;
						newY += -3;
						break;
			case 14:	newX += 2;
						newY += -3;
						break;
			case 15:	newX += 3;
						newY += -3;
						break;	
			case 16:	newX += 3;
						newY += -2;
						break;
			case 17:	newX += 3;
						newY += -1;
						break;
			default:	
						break;
		}

		return new Pair<Integer, Integer>(newX, newY);

	}
	
	/**
	 * Testing method for coordinate mapper
	 */
	public static void main(String[] args){
		CoordinateMapper teams2 = new CoordinateMapper(2);
		CoordinateMapper teams3 = new CoordinateMapper(3);
		CoordinateMapper teams6 = new CoordinateMapper(6);

		// Check that all thetas are being adjusted properly for all valid ranges
		for(int i = 0; i < 6 ; i++){
			assert( teams2.getAdjustedTheta(1, 1, i) == i);
			assert( teams2.getAdjustedTheta(2, 1, i) == ( (i + 3) % 6 ) );
			
			assert( teams3.getAdjustedTheta(1, 1, i) == i);
			assert( teams3.getAdjustedTheta(2, 1, i) == ( (i + 2) % 6 ) );
			assert( teams3.getAdjustedTheta(3, 1, i) == ( (i + 4) % 6 ) );
			
			assert( teams6.getAdjustedTheta(1, 1, i) == i);
			assert( teams6.getAdjustedTheta(2, 1, i) == ( (i + 1) % 6 ) );
			assert( teams6.getAdjustedTheta(3, 1, i) == ( (i + 2) % 6 ) );
			assert( teams6.getAdjustedTheta(4, 1, i) == ( (i + 3) % 6 ) );
			assert( teams6.getAdjustedTheta(5, 1, i) == ( (i + 4) % 6 ) );
			assert( teams6.getAdjustedTheta(6, 1, i) == ( (i + 5) % 6 ) );
		}
		
		for(int i = 0; i < 12 ; i++){
			assert( teams2.getAdjustedTheta(1, 2, i) == i);
			assert( teams2.getAdjustedTheta(2, 2, i) == ( (i + 6) % 12 ) );
		
			assert( teams3.getAdjustedTheta(1, 2, i) == i);
			assert( teams3.getAdjustedTheta(2, 2, i) == ( (i + 4) % 12 ) );
			assert( teams3.getAdjustedTheta(3, 2, i) == ( (i + 8) % 12 ) );
			
			assert( teams6.getAdjustedTheta(1, 2, i) == i);
			assert( teams6.getAdjustedTheta(2, 2, i) == ( (i + 2) % 12 ) );
			assert( teams6.getAdjustedTheta(3, 2, i) == ( (i + 4) % 12 ) );
			assert( teams6.getAdjustedTheta(4, 2, i) == ( (i + 6) % 12 ) );
			assert( teams6.getAdjustedTheta(5, 2, i) == ( (i + 8) % 12 ) );
			assert( teams6.getAdjustedTheta(6, 2, i) == ( (i + 10) % 12 ) );
		}
		
		for(int i = 0; i < 18 ; i++){
			assert( teams2.getAdjustedTheta(1, 3, i) == i);
			assert( teams2.getAdjustedTheta(2, 3, i) == ( (i + 9) % 18 ) );
			
			assert( teams3.getAdjustedTheta(1, 3, i) == i);
			assert( teams3.getAdjustedTheta(2, 3, i) == ( (i + 6) % 18 ) );
			assert( teams3.getAdjustedTheta(3, 3, i) == ( (i + 12) % 18 ) );
			
			assert( teams6.getAdjustedTheta(1, 3, i) == i);
			assert( teams6.getAdjustedTheta(2, 3, i) == ( (i + 3) % 18 ) );
			assert( teams6.getAdjustedTheta(3, 3, i) == ( (i + 6) % 18 ) );
			assert( teams6.getAdjustedTheta(4, 3, i) == ( (i + 9) % 18 ) );
			assert( teams6.getAdjustedTheta(5, 3, i) == ( (i + 12) % 18 ) );
			assert( teams6.getAdjustedTheta(6, 3, i) == ( (i + 15) % 18 ) );
		
		}
		
		
		// Output pairs to ensure mappings are correct
		System.out.println("\nRange of 1");
		
		/*
		for(int i = 0; i < 6; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams2.getCoords(0, 0, 1, i, 1) );
		}
		*/

		/*
		for(int i = 0; i < 6; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams3.getCoords(0, 0, 1, i, 1) );
		}
		*/
		
		/*
		for(int i = 0; i < 6; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams3.getCoords(0, 0, 1, 1, i) );
		}
		*/
		
		/*
		System.out.println( teams2.getCoords(0, 0, 1, 1, 1) );
		System.out.println( teams2.getCoords(0, 0, 1, 1, 2) );
		*/

		/*
		System.out.println( teams3.getCoords(0, 0, 2, 0, 1) );
		System.out.println( teams3.getCoords(0, 0, 2, 0, 2) );
		System.out.println( teams3.getCoords(0, 0, 2, 0, 3) );
		
		*/
		
		/*
		
		System.out.println("\n\nRange of 1");
		
		for(int i = 0; i < 6; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams6.getCoords(0, 0, 1, i, 1) );
			System.out.println( teams6.getCoords(0, 0, 1, i, 2) );
			System.out.println( teams6.getCoords(0, 0, 1, i, 3) );
			System.out.println( teams6.getCoords(0, 0, 1, i, 4) );
			System.out.println( teams6.getCoords(0, 0, 1, i, 5) );
			System.out.println( teams6.getCoords(0, 0, 1, i, 6) );
		}
		*/
		
		/*
		System.out.println("\n\nRange of 2");

		for(int i = 0; i < 12; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams6.getCoords(0, 0, 2, i, 1) );
			System.out.println( teams6.getCoords(0, 0, 2, i, 2) );
			System.out.println( teams6.getCoords(0, 0, 2, i, 3) );
			System.out.println( teams6.getCoords(0, 0, 2, i, 4) );
			System.out.println( teams6.getCoords(0, 0, 2, i, 5) );
			System.out.println( teams6.getCoords(0, 0, 2, i, 6) );
		}
		*/
		
		/*
		System.out.println("\n\nRange of 3");
		for(int i = 0; i < 18; i++){
			System.out.println( "Theta of:  " + i );
			System.out.println( teams6.getCoords(0, 0, 3, i, 1) );
			System.out.println( teams6.getCoords(0, 0, 3, i, 2) );
			System.out.println( teams6.getCoords(0, 0, 3, i, 3) );
			System.out.println( teams6.getCoords(0, 0, 3, i, 4) );
			System.out.println( teams6.getCoords(0, 0, 3, i, 5) );
			System.out.println( teams6.getCoords(0, 0, 3, i, 6) );
		}
		*/
		

	}
}

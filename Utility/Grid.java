package Utility;

public abstract class Grid {
	protected int[][] grid;
	
	public Grid() {
		grid = new int[10][10];
	}
	
	protected void hit(int[] coord) {
		
	}
	
	protected int[][] getGrid(){
		return grid;
	}

	protected void updateGrid(int[] coords,String type){
		//1 = shot coord
		//2 = ship coords

		//could also check to see what the size of coords is (eg, shots = size 2)
		if (type.equals("ShipGridPlaceShip")){ //ship grid
			for (int i = 0; i < coords.length - 1;i++){
				//first check to see if this is a valid placement (for the size of the ship (eg., 5 for a carrier))
				if (grid[coords[i]][coords[i+1]] == 2){
					badPlacement("Can't place place here");
					break;
				}
				else{
					grid[coords[i]][coords[i+1]] = 2;
				}
			}
		}
		else if(type.equals("ShootGrid")){ //shoot grid
			//first check to see if this is a valid placement
			if (grid[coords[0]][coords[1]] == 1){
				badPlacement("Can't place shot here");
			}
			else{
				grid[coords[0]][coords[1]] = 1;
			}
		}
		else{ //this is only to update the players ship grid when a shot is made
			grid[coords[0]][coords[1]] = 1;
		}
	}

	protected String badPlacement(String err){

		return err;
	}

}

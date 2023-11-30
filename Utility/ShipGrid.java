package Utility;

import java.util.ArrayList;

public class ShipGrid extends Grid{
	private ArrayList<Ship> ships;
	
	public ShipGrid(ArrayList<Ship> ships) {
		super();
		this.ships = ships;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public int[][] getGridasArray(){
		return super.getGrid();
	}

	public Feedback update(int[] lastShot, String type){
		//type = hit or miss
		//super.updateGrid(lastShot, type);
		grid = super.getGrid();
		int whatThere = grid[lastShot[0]][lastShot[1]];
		Feedback feedback = new Feedback("","");
		
		switch (whatThere) {
				
			case 20:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
					}
				}
					
				break;
				
			case 21:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
					}
				}
				break;
				
			case 22:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
					}
				}
				break;
			
			case 23:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
					}
				}
				break;
				
			case 24:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
					}
				}
				break;
				
			default:
				feedback.setMessage("You missed!");
				feedback.setType("miss");
				super.miss(lastShot);
				break;
		}
		
		return feedback;
	}
}

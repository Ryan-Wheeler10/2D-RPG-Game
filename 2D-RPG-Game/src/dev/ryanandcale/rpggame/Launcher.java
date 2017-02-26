package dev.ryanandcale.rpggame;


public class Launcher {

	public static void main(String[] args){
		Game game = new Game("Tile Game!", 1080, 720);
		game.start();
	}
	
}
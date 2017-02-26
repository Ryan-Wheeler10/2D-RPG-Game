package dev.ryanandcale.rpggame.states;

import java.awt.Graphics;

import dev.ryanandcale.rpggame.Game;
import dev.ryanandcale.rpggame.Handler;
import dev.ryanandcale.rpggame.entities.creatures.Player;
import dev.ryanandcale.rpggame.entities.statics.Tree;
import dev.ryanandcale.rpggame.tiles.Tile;
import dev.ryanandcale.rpggame.worlds.World;

public class GameState extends State {
	
	private World world;
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

}
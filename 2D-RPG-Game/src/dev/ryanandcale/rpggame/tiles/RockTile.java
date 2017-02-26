package dev.ryanandcale.rpggame.tiles;


import dev.ryanandcale.rpggame.gfx.Assets;

public class RockTile extends Tile{

	public RockTile(int id) {
		super(Assets.stone, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
		
	}

}

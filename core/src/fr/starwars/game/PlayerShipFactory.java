package fr.starwars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShipFactory {
    private TextureAtlas textureAtlas;
    private final static Texture blueShipTexture = new Texture("sprites/ships/blueships1_small.png");
    private final static Texture alienDroppingTexture= new Texture("sprites/ammo/aliendropping/aliendropping0001.png");

    public static Ship create(int screenHeight,int screenWidth) {
//        Ship playerShip = new Ship(2,3,screenWidth /2 , screenHeight/4,10,10,new Texture("sprites/ships/blueships1_small.png"));
    PlayerShip playerShip = new PlayerShip(2,3,screenWidth /2
            , screenHeight/4,10,10,0.5f,new TextureRegion(blueShipTexture),new TextureRegion(alienDroppingTexture)
            ,0.4f,4,45);

        return playerShip;
    }

}

package fr.starwars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShipFactory {

    private final static Texture roundyshTexture = new Texture("sprites/ships/roundysh_small.png");
    private final static Texture aleinDroppingTexture = new Texture("sprites/ammo/aliendropping/aliendropping0001.png");

    public static Ship create(int screenHeight,int screenWidth) {
//        Ship playerShip = new Ship(2,3,screenWidth /2 , screenHeight-screenHeight/4,10,10,new Texture("sprites/ships/roundysh_small.png"));
        EnemyShip playerShip = new EnemyShip(2,3,screenWidth /2
                , screenHeight-screenHeight/4,10
                ,10,0.8f
                ,new TextureRegion(roundyshTexture),new TextureRegion(aleinDroppingTexture),0.3f,5,50);
        return playerShip;
    }

}
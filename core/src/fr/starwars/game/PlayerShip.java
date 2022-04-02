package fr.starwars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship{
    public PlayerShip(float movementSpeed, int shield, float xCenter, float yCenter, float width, float height, float timeBetweenShots, TextureRegion texture, TextureRegion laserTexture, float laserWidth, float laserHeight, float laserMovementSpeed) {
        super(movementSpeed, shield, xCenter, yCenter, width, height, timeBetweenShots, texture, laserTexture, laserWidth, laserHeight, laserMovementSpeed);
    }

    @Override
    public Lazer[] fireLasers() {
        Lazer[] laser = new Lazer[2];
        laser[0] = new Lazer(xPosition+width*0.07f
                ,yPosition+height*0.45f,laserWidth,laserHeight,laserMovementSpeed,laserTexture);
        laser[1] = new Lazer(xPosition+width*0.93f,yPosition+height*0.45f
                ,laserWidth,laserHeight,laserMovementSpeed,laserTexture);

        timeSinceLastShot=0;

        return laser;
    }
}

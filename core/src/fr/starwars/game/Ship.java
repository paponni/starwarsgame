package fr.starwars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {
    //ship characteristics
    float movementSpeed;
    int shield;
    //position & dimension
    float xPosition, yPosition;
    float width, height;
    //graphics
    TextureRegion texture,laserTexture;
    double xSpeed = 0.4;
    private final int SCREEN_HEIGHT = 72;
    private final int SCREEN_WIDTH = 128;

    //laser information
    float laserWidth, laserHeight ;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0 ;


    public Ship(
            float movementSpeed, int shield,
            float xCenter, float yCenter,
            float width, float height,
            float timeBetweenShots,
            TextureRegion texture,TextureRegion laserTexture, float laserWidth,float laserHeight,float laserMovementSpeed) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCenter - width / 2;
        this.yPosition = yCenter - height / 2;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.laserTexture=laserTexture;
        this.laserWidth=laserWidth;
        this.laserHeight=laserHeight;
        this.laserMovementSpeed=laserMovementSpeed;
        this.timeBetweenShots=timeBetweenShots;
    }

    public void draw(Batch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
    }

    public void update(float deltaTime){
        timeSinceLastShot +=deltaTime;
    }

    public boolean canFireLaser(){
        return (timeSinceLastShot - timeBetweenShots >=0);
    }
    public abstract Lazer[] fireLasers();

    public boolean intersects(Rectangle otherRectangle){
        Rectangle thisRectangle = new Rectangle(xPosition,yPosition,width,height);
        return thisRectangle.overlaps(otherRectangle);
    }


    public void render() {
        xPosition += xSpeed;
        if (xPosition < 0 || xPosition > SCREEN_WIDTH) {
            xSpeed = -xSpeed;
        }
    }


    public void render_x(){
        xPosition   = Gdx.input.getX();
        if(xPosition < 0 || xPosition > SCREEN_WIDTH){
            xPosition = -xPosition;
        }

    }
    public boolean hitAndCheckDestroyed(Lazer lazer){
        if(shield > 0){
            shield --;
            return false;
        }
        return true;
    }


}


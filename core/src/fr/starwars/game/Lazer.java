package fr.starwars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lazer {

    //position and dimensions
    float xPosition,yPosition ;
    float width , height ;
    //lazer physical characterstics
    float mouvementSpeed ;

    //graphics
    TextureRegion textureRegion;

    public Lazer(float xPosition, float yPosition, float width, float height, float mouvementSpeed, TextureRegion textureRegion) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.mouvementSpeed = mouvementSpeed;
        this.textureRegion=textureRegion;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,xPosition,yPosition,width,height);
    }
}

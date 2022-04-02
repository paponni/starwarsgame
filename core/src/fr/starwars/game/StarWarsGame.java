package fr.starwars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class StarWarsGame extends ApplicationAdapter {
    private final int SCREEN_HEIGHT = 72;
    private final int SCREEN_WIDTH = 128;
    //SpriteBatch batch;
    Texture img;
    //screen
    private Camera camera;
    private Viewport viewport;
    //graphics
    private SpriteBatch batch;
    private Texture background;
    //timing
    private int backgroundOffset;

    //ships
    private Ship playerShip;
    private Ship enemyShip,enemyShip2;
    private LinkedList<Lazer> playerLazerList ;
    private LinkedList<Lazer> enemyLazerList;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        background = new Texture("backgrounds/Blue-Nebula.png");
        backgroundOffset = 0;
        batch = new SpriteBatch();
        playerShip = PlayerShipFactory.create(SCREEN_HEIGHT, SCREEN_WIDTH);
        enemyShip = EnemyShipFactory.create(SCREEN_HEIGHT, SCREEN_WIDTH);
        playerLazerList = new LinkedList<>();
        enemyLazerList = new LinkedList<>();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);
        batch.draw(background, 0, 0,SCREEN_WIDTH,SCREEN_HEIGHT);
        playerShip.draw(batch);
        enemyShip.draw(batch);
        playerShip.render_x();
        enemyShip.render();
        //lasers
        renderLasers(deltaTime);
        //explosions
        renderExplosions(deltaTime);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }


    public void renderLasers(float deltaTime){
        //player lasers
        if(playerShip.canFireLaser()){
            Lazer[] lazers = playerShip.fireLasers();
            for(Lazer lazer : lazers){
                playerLazerList.add(lazer);
            }
        }
        //enemy lasers
        if(enemyShip.canFireLaser()){
            Lazer[] lazers = enemyShip.fireLasers();
            for(Lazer lazer : lazers){
                enemyLazerList.add(lazer);
            }
        }
        //draw lasers
        //remove all lasers
        ListIterator<Lazer> iterator = playerLazerList.listIterator();
        while(iterator.hasNext()){
            Lazer lazer = iterator.next();
            System.out.println("im here");
            lazer.draw(batch);
            lazer.yPosition +=lazer.mouvementSpeed * deltaTime;
            if(lazer.yPosition > SCREEN_HEIGHT){
                iterator.remove();
            }
        }

        iterator = enemyLazerList.listIterator();
        while(iterator.hasNext()){
            Lazer lazer = iterator.next();
            System.out.println("im here 2");
            lazer.draw(batch);
            lazer.yPosition -=lazer.mouvementSpeed * deltaTime;
            if(lazer.yPosition + lazer.height < 0){
                iterator.remove();
            }
        }
    }

    public void renderExplosions(float deltaTime){

    }
}

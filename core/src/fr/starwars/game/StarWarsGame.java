package fr.starwars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

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
    private Texture explosionTexture ;
    //timing
    private int backgroundOffset;

    private float timeBetweenEnemySpawn = 3f;
    private float enemySpawnTimer = 0;

    //ships
    private Ship playerShip;
    private LinkedList<Ship> enemyShipList;
    private LinkedList<Lazer> playerLazerList ;
    private LinkedList<Lazer> enemyLazerList;
    private LinkedList<Explosion> explosionList ;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        background = new Texture("backgrounds/Blue-Nebula.png");
        backgroundOffset = 0;
        batch = new SpriteBatch();
        playerShip = PlayerShipFactory.create(SCREEN_HEIGHT, SCREEN_WIDTH);
        enemyShipList = new LinkedList<>();
        playerLazerList = new LinkedList<>();
        enemyLazerList = new LinkedList<>();
        explosionList = new LinkedList<>();
        explosionTexture = new Texture("sprites/kisspng-sprite-explosion_pack/explosion.png");
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        playerShip.update(deltaTime);
        batch.draw(background, 0, 0,SCREEN_WIDTH,SCREEN_HEIGHT);
        spawnEnemyShips(deltaTime);
        ListIterator<Ship> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()){
            Ship enemyShip = enemyShipListIterator.next();
            enemyShip.update(deltaTime);
            enemyShip.draw(batch);
            enemyShip.render();
        }

        playerShip.render_x();

        playerShip.draw(batch);

        //lasers
        renderLasers(deltaTime);
        //detect collisions
        detectCollision();
        //explosions
        renderExplosions(deltaTime);
        batch.end();
    }

    private void spawnEnemyShips(float deltaTime){
        enemySpawnTimer +=deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawn){
            enemyShipList.add(EnemyShipFactory.create(SCREEN_HEIGHT, SCREEN_WIDTH))  ;
            enemySpawnTimer -= timeBetweenEnemySpawn;
        }

    }

    private void detectCollision(){
        ListIterator<Lazer> iterator= playerLazerList.listIterator();
        while (iterator.hasNext()){
            Lazer lazer = iterator.next();
            ListIterator<Ship> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()){
                Ship enemyShip= enemyShipListIterator.next();
                if(enemyShip.intersects(lazer.getBoundingBox())){
                    if(enemyShip.hitAndCheckDestroyed(lazer)){
                        enemyShipListIterator.remove();
                        explosionList.add(new Explosion(explosionTexture
                                ,new Rectangle(enemyShip.xPosition,enemyShip.yPosition,enemyShip.width,enemyShip.height)
                                ,0.7f));

                    };
                    iterator.remove();
                    break;
                }
            }

        }
        iterator = enemyLazerList.listIterator();
        while (iterator.hasNext()){
            Lazer lazer = iterator.next();
            if(playerShip.intersects(lazer.getBoundingBox())){
                if(playerShip.hitAndCheckDestroyed(lazer)){
                    explosionList.add(new Explosion(explosionTexture
                            ,new Rectangle(playerShip.xPosition,playerShip.yPosition,playerShip.width,playerShip.height)
                            ,1.6f));
                    playerShip.shield =10;

                };
                iterator.remove();
                break;
            }
        }
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
        ListIterator<Ship> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()){
            Ship enemyShip = enemyShipListIterator.next();
            if(enemyShip.canFireLaser()){
                Lazer[] lazers = enemyShip.fireLasers();
                for(Lazer lazer : lazers){
                    enemyLazerList.add(lazer);
                }
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
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()){
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if(explosion.isFinished()){
                explosionListIterator.remove();
            }
            else {
                explosion.draw(batch);
            }
        }

    }
}

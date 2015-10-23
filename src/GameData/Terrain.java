/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameData;

import Controller.ExplosionObserver;
import GameObjects.Physics;
import GameObjects.Projectile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

/**
 * This is the class used for terrain for the game,
 * it can load and save the current terrain, for later playability
 * it has destructible terrain, and listens for explosions
 * @author o_0
 */
public class Terrain implements Observer {

    private Canvas mapCanvas;
    private Image background;
    private ArrayList<Circle> craters;
    double mapWidth;
    double mapHeigth;
    private boolean needsUpdate = false;

    /**
     * 
     * @param width the size of the terrain, width
     * @param heigth size for height
     */
    public Terrain(double width, double heigth) {
        this.craters = new ArrayList<Circle>();
        this.background = new Image("Resource/battleTerrain.png");  //defualt terrain
        this.mapHeigth = heigth;
        this.mapWidth = width;
        this.mapCanvas = new Canvas(mapWidth, mapHeigth);
        //this.background = Image();
        GraphicsContext gc = this.mapCanvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

    }

    /**
     * This will load a map, it will only load from the game directory
     * @param file the file info picked by fileChooser
     * @throws IOException if we fail to load our terrain
     */
    public void loadTerrain(File file) throws IOException {
        if (file == null) {
            return;
        }
        //Image tmpImg = background;
        String path = file.toURI().toString();
        this.background = new Image(path);
        this.needsUpdate = true;
        craters.clear();
    }

    /**
     * Saves the terrain for later loading
     * @param file the file to be saved
     * @throws IOException if it fails to save the file
     */
    public void saveTerrain(File file) throws IOException {
        if (file == null) {
            return;
        }

        BufferedImage buffer = SwingFXUtils.fromFXImage(this.background, null);
        ImageIO.write(buffer, "png", file);

    }

    /**
     * Checks if a point is inside the background
     * @param x x pos
     * @param y y pos
     * @return true if its valid point
     */
    private boolean isValidPoint(int x, int y) {
        if (x < 0 || background.getWidth() <= x || y < 0 || background.getHeight() <= y) {
            return false;
        }
        return true;
    }

    /**
     * This checks if a object is in contact with the terrain,
     * it does this by checking the pixels in the background if it is a skypixel
     * the pixels is based on the objects x,y and radius
     * @param obj object to check if it collides
     * @return true if it did collide
     */
    public boolean checkCollision(Physics obj) {
        PixelReader pr = background.getPixelReader();
        double scalingX = background.getWidth() / this.mapWidth;
        double scalingY = background.getHeight() / this.mapHeigth;
        Color sky = Color.LIGHTSKYBLUE;
        int centerX = (int) (obj.getX() * scalingX);
        int centerY = (int) (obj.getY() * scalingY);
        int radius = (int) (obj.getBodyRadius() * scalingX);

        int hitCount = 0;
        double avgX = 0;
        double avgY = 0;

        int tmpX = centerX;
        int tmpY = centerY - radius;
        if (isValidPoint(tmpX, tmpY) && !pr.getColor(tmpX, tmpY).equals(sky)) {
            avgX += tmpX;
            avgY += tmpY;
            hitCount++;
        }

        tmpX = centerX - radius;
        tmpY = centerY;
        if (isValidPoint(tmpX, tmpY) && !pr.getColor(tmpX, tmpY).equals(sky)) {
            avgX += tmpX;
            avgY += tmpY;
            hitCount++;
        }

        tmpX = centerX + radius;
        tmpY = centerY;
        if (isValidPoint(tmpX, tmpY) && !pr.getColor(tmpX, tmpY).equals(sky)) {
            avgX += tmpX;
            avgY += tmpY;
            hitCount++;
        }

        tmpX = centerX;
        tmpY = centerY + radius;
        if (isValidPoint(tmpX, tmpY) && !pr.getColor(tmpX, tmpY).equals(sky)) {
            avgX += tmpX;
            avgY += tmpY;
            hitCount++;
        }
        if (hitCount <= 0) {
            return false;
        }
        avgX = avgX / hitCount;
        avgY = avgY / hitCount;

        // tells the obj where the collision point is, scaled to game coordinates
        obj.collisionWithTerrainAt(avgX / scalingX, avgY / scalingY);
        
        return true;
    }

    /**
     * This returns the terrainImage, it only renders it into a new image
     * if something has changed, else it reuses the same image
     * @return returns the terrainImage
     */
    public Image getTerrainImage() {
        if (!this.needsUpdate) {
            return background;
        }
        this.needsUpdate = false;
        // Draw a cleen background
        GraphicsContext gc = this.mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapWidth, mapHeigth);
        gc.drawImage(background, 0,0,this.mapWidth, this.mapHeigth);
        
        // for all new creaters, paint sky color with crater radius,
        gc.setFill(Color.LIGHTSKYBLUE);   // 
        for (Circle crater : craters) {
            double radius = crater.getRadius();
            double x = crater.getCenterX() - radius;
            double y = crater.getCenterY() - radius;
            gc.fillOval(x, y, 2 * radius, 2 * radius);
            //gc.strokeOval(x,y, 2*radius, 2*radius);

        }
        craters.clear();
        // save canvase to a new background image
        background = mapCanvas.snapshot(null, null);
        return background;
    }

    /**
     * Is notified if a explosion has happen
     * Adds all explosions to a crater
     * @param o the observable
     * @param arg not used
     */
    @Override
    public void update(Observable o, Object arg) {
        Projectile exploded = ((ExplosionObserver) o).getProjectile();
        Circle crater = new Circle(exploded.getX(),
                exploded.getY(),
                exploded.getDamageRadius());
        craters.add(crater);
        this.needsUpdate = true;
    }

}

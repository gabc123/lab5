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
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;

/**
 *
 * @author o_0
 */
public class Terrain implements Observer {

    private Canvas mapCanvas;
    private Image background;
    private ArrayList<Circle> craters;
    double mapWidth;
    double mapHeigth;
    private boolean needsUpdate = false;

    public Terrain(double width, double heigth) {
        this.craters = new ArrayList<Circle>();
        this.background = new Image("Resource/battleTerrain.png");
        this.mapHeigth = heigth;
        this.mapWidth = width;
        this.mapCanvas = new Canvas(mapWidth, mapHeigth);
        //this.background = Image();
        GraphicsContext gc = this.mapCanvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

    }

    public void loadTerrain(File file) throws IOException {
        if (file == null) {
            return;
        }
        //Image tmpImg = background;
        String path = file.getName();
        this.background = new Image(path);
        this.needsUpdate = true;
        craters.clear();
    }

    public void saveTerrain(File file) throws IOException {
        if (file == null) {
            return;
        }

        BufferedImage buffer = SwingFXUtils.fromFXImage(this.background, null);
        ImageIO.write(buffer, "png", file);

    }

    private boolean isValidPoint(int x, int y) {
        if (x < 0 || background.getWidth() <= x || y < 0 || background.getHeight() <= y) {
            return false;
        }
        return true;
    }

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

        obj.collisionWithTerrainAt(avgX / scalingX, avgY / scalingY);
        
        return true;
    }

    public Image getTerrainImage() {
        if (!this.needsUpdate) {
            return background;
        }
        this.needsUpdate = false;
        GraphicsContext gc = this.mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapWidth, mapHeigth);
        gc.drawImage(background, 0,0,this.mapWidth, this.mapHeigth);
        //Paint paint = new Paint();
        //Color.li
        gc.setFill(Color.LIGHTSKYBLUE);   // 
        for (Circle crater : craters) {
            double radius = crater.getRadius();
            double x = crater.getCenterX() - radius;
            double y = crater.getCenterY() - radius;
            gc.fillOval(x, y, 2 * radius, 2 * radius);
            //gc.strokeOval(x,y, 2*radius, 2*radius);

        }
        craters.clear();
        background = mapCanvas.snapshot(null, null);
        return background;
    }

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

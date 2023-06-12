package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Enemy;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileManager {

    ArrayList<Projectile> projectiles = new ArrayList<>();

    GamePanel gamePanel;

    public ProjectileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void createProjectile(int worldX, int worldY, int targetX, int targetY, int speed, BufferedImage image,
                                 Entity owner) {
        projectiles.add(new Projectile(gamePanel, worldX, worldY, targetX, targetY, speed, image, owner));
    }


    public void update() {

        if (projectiles.size() > 50) {
            clearOldProjectiles(50);
        }

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile item = iterator.next();
            if (item.mustBeDeleted) {
                iterator.remove();
            }
            else {
                item.update();
            }
        }


    }


    void clearOldProjectiles(int projectileCount) {
        projectiles.remove(0);
        if (projectiles.size() > projectileCount) {
            clearOldProjectiles(projectileCount);
        }
    }



    public void draw(Graphics2D g2) {
        ArrayList<Projectile> projectilesCopy = new ArrayList<>(projectiles);
        Iterator<Projectile> iterator = projectilesCopy.iterator();
        while (iterator.hasNext()) {
            Projectile item = iterator.next();
            if ( item != null) {
                item.draw(g2);
            }
        }
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}

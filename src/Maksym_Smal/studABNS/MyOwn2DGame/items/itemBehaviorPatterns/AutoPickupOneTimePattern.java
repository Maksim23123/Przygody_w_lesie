package Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.AttributeModifier;
import Maksym_Smal.studABNS.MyOwn2DGame.items.Item;

public class AutoPickupOneTimePattern extends ItemBehaviorPattern {

    public AutoPickupOneTimePattern(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void update(Item item) {
        whenDistance(item, gamePanel.player.worldX, gamePanel.player.worldY);
    }

    public void whenDistance(Item item, int cordX, int cordY) {
        double distanceY = item.worldY - cordY;
        double distanceX = item.worldX - cordX;

        double targetDistance = Math.sqrt(Math.pow(distanceX, 2) +
                Math.pow(distanceY, 2));


        double cosA = distanceY / targetDistance;
        double sinA = distanceX / targetDistance;

        int speed = 10;

        if (targetDistance == 0) {
            item.worldX += 1;
            item.worldY += 1;
        }
        if (targetDistance < 32) {
            whenPuckedUp(item, item.gamePanel);
        }
        else if (targetDistance < 64 * 3) {
            item.worldX += speed * sinA * -1;
            item.worldY += speed * cosA * -1;
        }
    }

    void whenPuckedUp(Item item ,GamePanel gamePanel) {
        item.inDeleteQuery = true;

        gamePanel.soundManager.playSound(2);
        AttributeModifier attributeModifier = new AttributeModifier(item.getItemAttributeManager());
        attributeModifier.setPermanent(false);
        attributeModifier.setInfinity(false);
        attributeModifier.setDuration(1);

        gamePanel.player.attributeModifiers.add(attributeModifier);
    }
}

package Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.Item;

public class PickupByHandPermanentPattern extends ItemBehaviorPattern{

    String defaultImageTag = null;

    String activeItemImageTag;

    public PickupByHandPermanentPattern(GamePanel gamePanel, String activeItemImageTag) {
        super(gamePanel);
        this.activeItemImageTag = activeItemImageTag;
    }

    @Override
    public void update(Item item) {
        if (defaultImageTag == null) {
            defaultImageTag = item.getImageTag();
        }

        int mouseCordX = ((gamePanel.player.worldX - gamePanel.player.screenX) + gamePanel.mouseHandler.getMousePosX())
                - (int)((gamePanel.tileSize / 1.5f) / 2f);
        int mouseCordY = ((gamePanel.player.worldY - gamePanel.player.screenY) + gamePanel.mouseHandler.getMousePosY())
                - (int)((gamePanel.tileSize / 1.5f) / 2f);


        double distanceToMouse = Math.sqrt(Math.pow(item.worldX - mouseCordX, 2) +
                Math.pow(item.worldY - mouseCordY, 2));

//        System.out.println(distanceToMouse);

        if (distanceToMouse < (gamePanel.tileSize / 1.5f) / 2) {
            item.setImageTag(activeItemImageTag);
            if (gamePanel.mouseHandler.getPressedButtonsQueue().contains("mouse3")) {
                item.setImageTag(defaultImageTag);
                item.itemBehaviorPattern = new AutoPickupPermanentPattern(gamePanel);
            }
        }
        else {
            item.setImageTag(defaultImageTag);
        }
    }
}

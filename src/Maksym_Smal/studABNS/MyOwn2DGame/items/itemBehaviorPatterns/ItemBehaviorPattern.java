package Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.Item;

public class ItemBehaviorPattern {

    GamePanel gamePanel;

    public ItemBehaviorPattern(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update(Item item) {};

}

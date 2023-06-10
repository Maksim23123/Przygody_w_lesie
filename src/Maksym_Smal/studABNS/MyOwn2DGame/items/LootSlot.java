package Maksym_Smal.studABNS.MyOwn2DGame.items;

public class LootSlot extends Item {

    int dropChance;

    LootSlot(Item item, int dropChance) {
        super(item);

        this.dropChance = dropChance;
    }
}

package Maksym_Smal.studABNS.MyOwn2DGame.items;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.AttributeManager;

public class ItemAttributeManager extends AttributeManager {

    ItemAttributeManager() {
        attackCooldown = 0;
        damage = 0;
        damageMultiple = 0;
        inputDamageMultiple = 0;
        health = 0;
        maxHealth = 0;
        healthRegeneration = 0;
    }

    @Override
    public void setInputDamageMultiple(double inputDamageMultiple) {
        this.inputDamageMultiple = inputDamageMultiple;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

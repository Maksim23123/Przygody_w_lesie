package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.items.ItemAttributeManager;

public class AttributeModifier extends AttributeManager {

    private boolean permanent = false;

    private boolean isInfinity = false;

    private int duration = 1;

    public AttributeModifier() {
        attackCooldown = 0;
        damage = 0;
        damageMultiple = 0;
        inputDamageMultiple = 0;
        health = 0;
        maxHealth = 0;
        healthRegeneration = 0;
    }

    public AttributeModifier(AttributeManager attributeManager) {
        speed = attributeManager.getSpeed();

        attackCooldown = attributeManager.getAttackCooldown();
        attackFarRange = attributeManager.getAttackFarRange();
        attackRange = attributeManager.getAttackRange();

        damage = attributeManager.getDamage();
        damageMultiple = attributeManager.getDamageMultiple();

        inputDamage = attributeManager.getInputDamage();
        inputDamageMultiple = attributeManager.getInputDamageMultiple();

        maxHealth = attributeManager.getMaxHealth();
        health = attributeManager.getHealth();

        healthRegeneration = attributeManager.getHealthRegeneration();
        healthIsRegenerating = attributeManager.getHealthIsRegenerating();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isInfinity() {
        return isInfinity;
    }

    public int getDuration() {
        return duration;
    }

    public void setInfinity(boolean infinity) {
        if (true) {
            setPermanent(false);
        }

        isInfinity = infinity;
    }

    public void setPermanent(boolean permanent) {
        if (permanent) {
            setInfinity(false);
        }

        this.permanent = permanent;
    }

    public boolean isPermanent() {
        return permanent;
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

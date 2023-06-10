package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.Random;

import java.io.Serializable;

public class AttributeManager implements Serializable {

    protected int speed;

    protected int attackCooldown = 1;
    protected int attackFarRange;
    protected int attackRange;

    protected int damage = 1;
    protected float damageMultiple = 1;
    protected int inputDamage;
    protected double inputDamageMultiple = 1;

    protected int health = 1;
    protected int maxHealth = 1;
    protected int healthRegeneration = 1;
    protected boolean healthIsRegenerating = false;

    private int regenerationTime = 120;

    private int immortalTime = 0;

    private int startImmortalTime = 4;

    public AttributeManager() {}




    public AttributeManager(AttributeManager attributeManager) {
        setSpeed(attributeManager.getSpeed());

        setAttackCooldown(attributeManager.getAttackCooldown());
        setAttackFarRange(attributeManager.getAttackFarRange());
        setAttackRange(attributeManager.getAttackRange());

        setDamage(attributeManager.getDamage());
        setDamageMultiple(attributeManager.getDamageMultiple());

        setInputDamage(attributeManager.getInputDamage());
        setInputDamageMultiple(attributeManager.getInputDamageMultiple());

        setMaxHealth(attributeManager.getMaxHealth());
        setHealth(attributeManager.getHealth(), true);

        setHealthRegeneration(attributeManager.getHealthRegeneration());
        setHealthIsRegenerating(attributeManager.getHealthIsRegenerating());
        regenerationTime = attributeManager.getRegenerationTime();
        startImmortalTime = attributeManager.getStartImmortalTime();
    }

    public String showAttributes() {
        return "attackCooldown: " + attackCooldown + "\n" +
                "damage: " + damage + "\n" +
                "damageMultiple: " + damageMultiple + "\n" +
                "inputDamage: " + inputDamage + "\n" +
                "inputDamageMultiple: " + inputDamageMultiple + "\n" +
                "health: " + health + "\n" +
                "maxHealth: " + maxHealth + "\n" +
                "healthRegeneration: " + healthRegeneration + "\n" +
                "healthIsRegenerating: " + healthIsRegenerating + "\n" +
                "speed: " + speed + "\n" +
                "attackFarRange: " + attackFarRange + "\n" +
                "attackRange: " + attackRange;
    }

    void update() {

        if (inputDamage > 0 && !(immortalTime > 0)) {
            health -= (int)((double)(inputDamage) * inputDamageMultiple);
            immortalTime = startImmortalTime;
            inputDamage = 0;
        }
        else if (healthIsRegenerating && regenerationTime <= 0) {
            health += healthRegeneration;
            regenerationTime = 120;
        }
        else if (healthIsRegenerating) {
            regenerationTime--;
        }

        if (immortalTime > 0) {
            inputDamage = 0;
            immortalTime--;
        }
    }

    void setRandomAttributes(int level) {
        setAttackCooldown(20 - Random.getRandomInt(5, true) * level);

        setDamage(2 * (level - 1) + Random.getRandomInt(5, true));

        setSpeed(6 + Random.getRandomInt(4, false));

        setMaxHealth(10 * (level - 1) + Random.getRandomInt(20, false));
        setHealth(maxHealth, false);

        setAttackRange(30);
        setAttackFarRange(48 * 3);
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth >= 1) {
            this.maxHealth = maxHealth;
        }
        else {
            this.maxHealth = 1;
        }
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackFarRange() {
        return attackFarRange;
    }

    public void setAttackFarRange(int attackFarRange) {
        if (attackFarRange >= 0) {
            this.attackFarRange = attackFarRange;
        }
        else {
            this.attackFarRange = 0;
        }
    }

    public void setAttackRange(int attackRange) {
        if (attackRange < 0) {
            throw new IllegalArgumentException("Degree lower then zero");
        }
        else if (attackRange > 360) {
            setAttackRange(attackRange - 360);
        }
        else {
            this.attackRange = attackRange;
        }
    }

    public void setHealth(int health, boolean ignoreLimits) {
        if (health > maxHealth) {
            if (ignoreLimits) {
                this.health = health;
            }
            else {
                this.health = maxHealth;
            }
        }
        else {
            this.health = health;
        }
    }

    public int getHealth() {
        return health;
    }

    public int getOutputDamage() {
        return (int)(damage * damageMultiple);
    }

    public void dealDamage(int inputDamage) {
        this.inputDamage += inputDamage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttackCooldown(int attackCooldown) {
        if (attackCooldown >= 5) {
            this.attackCooldown = attackCooldown;
        }
        else {
            this.attackCooldown = 5;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void marge(AttributeManager attributeManager) {
        setSpeed(getSpeed() + attributeManager.getSpeed());

        setAttackCooldown(getAttackCooldown() + attributeManager.getAttackCooldown());
        setAttackFarRange(getAttackFarRange() + attributeManager.getAttackFarRange());
        setAttackRange(getAttackRange() + attributeManager.getAttackRange());

        setDamage(getDamage() + attributeManager.getDamage());
        setDamageMultiple(getDamageMultiple() + attributeManager.getDamageMultiple());

        setInputDamage(getInputDamage() + attributeManager.getInputDamage());
        setInputDamageMultiple(getInputDamageMultiple() + attributeManager.getInputDamageMultiple());

        setHealth(getHealth() + attributeManager.getHealth(), false);

        setMaxHealth(getMaxHealth() + attributeManager.getMaxHealth());
        setHealthRegeneration(getHealthRegeneration() + attributeManager.getHealthRegeneration());

        if (attributeManager.getHealthIsRegenerating()) {
            setHealthIsRegenerating(true);
        }
    }

    public void setHealthRegeneration(int healthRegeneration) {
        this.healthRegeneration = healthRegeneration;
    }

    public void setRegenerationTime(int regenerationTime) {
        this.regenerationTime = regenerationTime;
    }

    public void setHealthIsRegenerating(boolean healthIsRegenerating) {
        this.healthIsRegenerating = healthIsRegenerating;
    }

    public boolean getHealthIsRegenerating() {
        return healthIsRegenerating;
    }

    public int getHealthRegeneration() {
        return healthRegeneration;
    }

    public void setInputDamageMultiple(double inputDamageMultiple) {
        if (inputDamageMultiple > 0.001d) {
            this.inputDamageMultiple = inputDamageMultiple;
        }
        else {
            this.inputDamageMultiple = 0.001d;
        }
    }

    public double getInputDamageMultiple() {
        return inputDamageMultiple;
    }

    public void setInputDamage(int inputDamage) {
        this.inputDamage = inputDamage;
    }

    public int getInputDamage() {
        return inputDamage;
    }

    public float getDamageMultiple() {
        return damageMultiple;
    }

    public void setDamageMultiple(float damageMultiple) {
        this.damageMultiple = damageMultiple;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRegenerationTime() {
        return regenerationTime;
    }

    public int getImmortalTime() {
        return immortalTime;
    }

    public void setImmortalTime(int immortalTime) {
        this.immortalTime = immortalTime;
    }

    public int getStartImmortalTime() {
        return startImmortalTime;
    }

    public void setStartImmortalTime(int startImmortalTime) {
        this.startImmortalTime = startImmortalTime;
    }
}

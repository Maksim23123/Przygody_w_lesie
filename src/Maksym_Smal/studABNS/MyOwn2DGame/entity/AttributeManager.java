package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.Random;

public class AttributeManager {

    public int speed;

    public int attackCooldown = 1;
    private int attackFarRange;
    private int attackRange;

    public int damage = 1;
    public float damageMultiple = 1;
    private int inputDamage;
    float inputDamageMultiple = 1;

    private int health = 1;
    private int maxHealth = 1;
    private int healthRegeneration = 1;
    public boolean healthIsRegenerating = false;

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

        health -= inputDamage * inputDamageMultiple;
        inputDamage = 0;

    }

    void setRandomAttributes(int level) {
        setAttackCooldown(20 - Random.getRandomInt(5, true) * level);

        setDamage(2 * (level - 1) + Random.getRandomInt(5, true));

        setSpeed(6 + Random.getRandomInt(4, false));

        setMaxHealth(10 * (level - 1) + Random.getRandomInt(20, false));
        setHealth(maxHealth, false);

        setAttackRange(30);
        setAttackFarRange(48 * (2 + Random.getRandomInt(3, false)));
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

    void setHealth(int health, boolean ignoreLimits) {
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
}

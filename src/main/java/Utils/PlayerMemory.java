package Utils;

import java.util.HashMap;
import java.util.Map;

public class PlayerMemory {
    public double getHealth() {
        return Health;
    }

    public Integer getHardness() {
        return Hardness;
    }

    public Integer getAggression() {
        return Aggression;
    }

    public Integer getMana() {
        return mana;
    }

    public Integer getGOLD() {
        return GOLD;
    }

    public void setHealth(double health) {
        Health = health;
    }

    public void setHardness(Integer hardness) {
        Hardness = hardness;
    }

    public void setAggression(Integer aggression) {
        Aggression = aggression;
    }

    public void setMana(Integer manad) {
        mana = manad;
    }

    public void setGOLD(Integer GOLDd) {
        GOLD = GOLDd;
    }
    public Integer getInvulnerability(){ return invulnerability;}
    public void setInvulnerability(Integer invulnerability1){ invulnerability = invulnerability1;}
    private double Health = 100;

    public double getMaxHealth() {
        return MaxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        MaxHealth = maxHealth;
    }

    private double MaxHealth = 100;
    private Integer Hardness = 1;
    public Integer invulnerability = 0;
    private Integer Aggression = 0;
    private final Map<Integer, Integer> classLvls = new HashMap<>();

    private final Map<Integer, Integer> professions = new HashMap<>();
    private Integer mana = 100;
    private Integer GOLD = 0;

    public void init() {
        //Necromancy
        classLvls.put(1, 0);
        //Sorcery
        classLvls.put(2, 0);
        //Healing
        classLvls.put(3, 0);
        //Battle
        classLvls.put(4, 0);

        //Mining
        professions.put(1, 0);
        //Fishing
        professions.put(2, 0);
        //Logger
        professions.put(3, 0);
        //Farming
        professions.put(4, 0);

    }

    public void Damage(Integer dmg) {
        Integer toReduce = ((dmg * (1-(Hardness / (Hardness + 50)))) - invulnerability) >= 0 ? ((Math.round(dmg * (1-(Hardness / (float) (Hardness + 50))))) - invulnerability) : 0;
        if (Health - toReduce < 0) Health = 0;
        else Health -= toReduce;
    }

    public float dealDamage(Integer weaponBaseDamage, Integer weaponClass) {
        return (float) weaponBaseDamage * Aggression / 10 * 1 + (1 + (float) classLvls.get(weaponClass)) / 10;
    }


}

package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.powers.StoneSkinPower;
import vexMod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RandomRelic extends CustomRelic implements OnLoseBlockRelic, OnPlayerDeathRelic, CustomSavable<Integer[]> {


    public static final String ID = VexMod.makeID("RandomRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RandomRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RandomRelic.png"));

    private int ID_MINOR;
    private int ID_MAJOR;
    private boolean triggered;

    public RandomRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void onEquip() {
        ArrayList<String> list = new ArrayList<>();
        if (AbstractDungeon.player instanceof TheSilent) {
            list.add("CombatStartPoison");
        }
        if (AbstractDungeon.player instanceof Defect) {
            list.add("OrbSlotGain");
        }
        if (AbstractDungeon.player instanceof Ironclad) {
            list.add("CombatTempStrength");
        }
        list.add("TurnStartDamage");
        list.add("CombatStartBlockGain");
        list.add("CombatStartDamage");
        list.add("CombatStartDraw");
        list.add("BlockBrokenHeal");
        list.add("CombatStartBlock");
        list.add("CombatStartEnergy");
        list.add("UponPickupMaxHP");
        list.add("CombatStartThorns");
        list.add("CombatStartHeal");
        list.add("?RoomChest");
        list.add("CombatStartColorlessCard");
        list.add("DeathAversionHealth");
        list.add("KillGoldGain");
        list.add("PickupPotionBrew");
        list.add("PickupGold");
        list.add("TurnStartBlock");
        list.add("CombatStartSnakeSkin");
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
        switch (list.get(0)) {
            case "CombatStartBlockGain":
                ID_MINOR = 0;
                break;
            case "CombatStartPoison":
                ID_MINOR = 1;
                break;
            case "TurnStartDamage":
                ID_MINOR = 2;
                break;
            case "CombatStartDamage":
                ID_MINOR = 3;
                break;
            case "CombatStartDraw":
                ID_MINOR = 4;
                break;
            case "BlockBrokenHeal":
                ID_MINOR = 5;
                break;
            case "CombatStartBlock":
                ID_MINOR = 6;
                break;
            case "CombatStartEnergy":
                ID_MINOR = 7;
                break;
            case "UponPickupMaxHP":
                ID_MINOR = 8;
                break;
            case "CombatStartThorns":
                ID_MINOR = 9;
                break;
            case "CombatStartHeal":
                ID_MINOR = 10;
                break;
            case "?RoomChest":
                ID_MINOR = 11;
                break;
            case "CombatStartColorlessCard":
                ID_MINOR = 12;
                break;
            case "KillGoldGain":
                ID_MINOR = 13;
                break;
            case "PickupPotionBrew":
                ID_MINOR = 14;
                break;
            case "PickupGold":
                ID_MINOR = 15;
                break;
            case "TurnStartBlock":
                ID_MINOR = 16;
                break;
            case "CombatStartSnakeSkin":
                ID_MINOR = 17;
                break;
            case "DeathAversionHealth":
                ID_MINOR = 18;
                break;
            case "OrbSlotGain":
                ID_MINOR = 19;
                break;
            case "CombatTempStrength":
                ID_MINOR = 20;
                break;
        }
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
        switch (list.get(0)) {
            case "CombatStartBlockGain":
                ID_MAJOR = 0;
                break;
            case "CombatStartPoison":
                ID_MAJOR = 1;
                break;
            case "TurnStartDamage":
                ID_MAJOR = 2;
                break;
            case "CombatStartDamage":
                ID_MAJOR = 3;
                break;
            case "CombatStartDraw":
                ID_MAJOR = 4;
                break;
            case "BlockBrokenHeal":
                ID_MAJOR = 5;
                break;
            case "CombatStartBlock":
                ID_MAJOR = 6;
                break;
            case "CombatStartEnergy":
                ID_MAJOR = 7;
                break;
            case "UponPickupMaxHP":
                ID_MAJOR = 8;
                break;
            case "CombatStartThorns":
                ID_MAJOR = 9;
                break;
            case "CombatStartHeal":
                ID_MAJOR = 10;
                break;
            case "?RoomChest":
                ID_MAJOR = 11;
                break;
            case "CombatStartColorlessCard":
                ID_MAJOR = 12;
                break;
            case "KillGoldGain":
                ID_MAJOR = 13;
                break;
            case "PickupPotionBrew":
                ID_MAJOR = 14;
                break;
            case "PickupGold":
                ID_MAJOR = 15;
                break;
            case "TurnStartBlock":
                ID_MAJOR = 16;
                break;
            case "CombatStartSnakeSkin":
                ID_MAJOR = 17;
                break;
            case "DeathAversionHealth":
                ID_MAJOR = 18;
                break;
            case "OrbSlotGain":
                ID_MAJOR = 19;
                break;
            case "CombatTempStrength":
                ID_MAJOR = 20;
                break;
        }
        if (ID_MINOR == 8) {
            AbstractDungeon.player.increaseMaxHp(5, true);
        }
        if (ID_MAJOR == 8) {
            AbstractDungeon.player.increaseMaxHp(9, true);
        }
        if (ID_MINOR == 14) {
            AbstractDungeon.player.obtainPotion(PotionHelper.getRandomPotion());
        }
        if (ID_MAJOR == 14) {
            AbstractDungeon.player.obtainPotion(PotionHelper.getRandomPotion());
            AbstractDungeon.player.obtainPotion(PotionHelper.getRandomPotion());
            AbstractDungeon.player.obtainPotion(PotionHelper.getRandomPotion());
        }
        if (ID_MINOR == 11) {
            EventHelper.TREASURE_CHANCE += 0.04F;
        }
        if (ID_MAJOR == 11) {
            EventHelper.TREASURE_CHANCE += 0.08F;
        }
        if (ID_MINOR == 15) {
            CardCrawlGame.sound.play("GOLD_GAIN");
            AbstractDungeon.player.gainGold(40);
        }
        if (ID_MAJOR == 15) {
            CardCrawlGame.sound.play("GOLD_GAIN");
            AbstractDungeon.player.gainGold(125);
        }
        this.description = DESCRIPTIONS[ID_MAJOR] + " NL " + DESCRIPTIONS[ID_MINOR + 21] + " NL " + DESCRIPTIONS[42];
        this.flavorText = DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(98, 167)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(168, 215)] + " " + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(43, 54)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(55, 97)];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo info) {
        if (ID_MINOR == 18 && this.counter == -1) {
            this.counter = -2;
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth / 10);
            return false;
        }
        if (ID_MAJOR == 18 && this.counter == -1) {
            this.counter = -2;
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth / 3);
            return false;
        }
        return true;
    }

    public Integer[] onSave() {
        return new Integer[]{ID_MINOR, ID_MAJOR};
    }

    @Override
    public void onLoad(Integer[] integerList) {
        ID_MINOR = integerList[0];
        ID_MAJOR = integerList[1];
        this.description = DESCRIPTIONS[ID_MAJOR] + " NL " + DESCRIPTIONS[ID_MINOR + 21] + " NL " + DESCRIPTIONS[42];
        this.flavorText = DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(98, 167)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(168, 215)] + " " + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(43, 54)] + DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(55, 97)];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public int onLoseBlock(DamageInfo info, int damageAmount) {
        if (!triggered && damageAmount >= AbstractDungeon.player.currentBlock && ID_MINOR == 5 && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
            this.triggered = true;
        }
        if (!triggered && damageAmount >= AbstractDungeon.player.currentBlock && ID_MAJOR == 5 && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            this.triggered = true;
        }
        return damageAmount;
    }

    @Override
    public void atBattleStart() {
        this.triggered = false;
        if (ID_MINOR == 0) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 5));
        }
        if (ID_MAJOR == 0) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 9));
        }
        if (ID_MINOR == 1) {
            this.flash();

            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, 3), 3));
                }
            }
        }
        if (ID_MAJOR == 1) {
            this.flash();

            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, 4), 4));
                }
            }
        }
        if (ID_MINOR == 3) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (ID_MAJOR == 3) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(9, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (ID_MINOR == 4) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        if (ID_MAJOR == 4) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
        }
        if (ID_MINOR == 6) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 2), 2));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MAJOR == 6) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 3), 3));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MINOR == 7) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MAJOR == 7) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
        }
        if (ID_MINOR == 9) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 1), 1));
        }
        if (ID_MAJOR == 9) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 2), 2));
        }
        if (ID_MINOR == 10) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
        if (ID_MAJOR == 10) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
        }
        if (ID_MINOR == 12) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomColorlessCardInCombat().makeCopy(), false));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MAJOR == 12) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat().makeCopy();
            c.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, false));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MINOR == 17) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StoneSkinPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MAJOR == 17) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StoneSkinPower(AbstractDungeon.player, AbstractDungeon.player, 2), 2));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MINOR == 19) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
        }
        if (ID_MAJOR == 19) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
        }
        if (ID_MINOR == 20) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 2), 2));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        if (ID_MAJOR == 20) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 4), 4));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 4), 4));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (ID_MINOR == 13) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            AbstractDungeon.player.gainGold(3);
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < 3; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
            }

        }
        if (ID_MAJOR == 13) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            AbstractDungeon.player.gainGold(6);
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < 6; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
            }

        }
    }

    @Override
    public void atTurnStart() {
        if (ID_MINOR == 2) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(1, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (ID_MAJOR == 2) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (ID_MINOR == 16) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
        if (ID_MAJOR == 16) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[216];
    }

}

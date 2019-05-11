package vexMod.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class GoldPotion extends AbstractPotion {


    public static final String POTION_ID = vexMod.VexMod.makeID("GoldPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public GoldPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.M, PotionColor.BLUE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = true;

        targetRequired = true;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(target);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AbstractGameAction.AttackEffect.SMASH));
        int coins = AbstractDungeon.cardRandomRng.random(25, 50);
        AbstractDungeon.player.gainGold(coins);
        if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
            for (int i = 0; i < coins; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, target.hb.cX, target.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new GoldPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 10;
    }

}
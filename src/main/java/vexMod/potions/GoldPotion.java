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

    private GoldPotion() {

        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.M, PotionColor.BLUE);


        potency = getPotency();


        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];


        isThrown = true;

        targetRequired = true;


        tips.add(new PowerTip(name, description));

    }


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


    @Override
    public int getPotency(final int potency) {
        return 10;
    }

}
package vexMod.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.actions.BlazeAction;

public class BlazePotion extends AbstractPotion {


    public static final String POTION_ID = vexMod.VexMod.makeID("BlazePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private BlazePotion() {

        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.BLUE);


        potency = getPotency();


        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];


        isThrown = false;


        tips.add(new PowerTip(name, description));

    }


    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new BlazeAction(AbstractDungeon.player, 2, false));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BlazePotion();
    }


    @Override
    public int getPotency(final int potency) {
        return 2;
    }

}
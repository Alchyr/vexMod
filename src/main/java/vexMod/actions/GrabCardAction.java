package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GrabCardAction extends AbstractGameAction {
    private AbstractRelic botling;

    public GrabCardAction(AbstractRelic EvilRelic) {
        duration = Settings.ACTION_DUR_MED;
        botling = EvilRelic;
    }

    @Override
    public void update() {
        AbstractCard c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (c != null) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(botling, (botling.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(13, 16)] + c.name + ", " + botling.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            botling.flash();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, botling));
        }
        this.isDone = true;
    }
}
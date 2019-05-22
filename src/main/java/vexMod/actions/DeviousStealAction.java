package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.relics.DeviousBotling;

public class DeviousStealAction extends AbstractGameAction {

    public DeviousStealAction() {
        duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        AbstractCard c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (c != null) {
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(AbstractDungeon.player.getRelic(DeviousBotling.ID), (AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(13, 16)] + c.name + ", " + AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            AbstractDungeon.player.getRelic(DeviousBotling.ID).flash();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(DeviousBotling.ID)));
        }
        this.isDone = true;
    }
}
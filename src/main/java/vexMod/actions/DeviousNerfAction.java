package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.relics.DeviousBotling;

public class DeviousNerfAction extends AbstractGameAction {

    public DeviousNerfAction() {
        duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.getAttacks().size() > 0) {
            AbstractCard c = AbstractDungeon.player.hand.getAttacks().getRandomCard(false);
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(AbstractDungeon.player.getRelic(DeviousBotling.ID), (AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(17, 20)] + c.name + ", " + AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            AbstractDungeon.player.getRelic(DeviousBotling.ID).flash();
            int downTown = AbstractDungeon.cardRandomRng.random(3, 4);
            c.baseDamage -= downTown;
            c.superFlash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(DeviousBotling.ID)));
            c.superFlash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new DeviousStealAction());
        }
        this.isDone = true;
    }
}
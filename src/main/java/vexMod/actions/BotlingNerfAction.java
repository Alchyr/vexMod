package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.cards.GhostlyBlitz;
import vexMod.relics.DeviousBotling;

public class BotlingNerfAction extends AbstractGameAction {
    AbstractRelic botling;

    public BotlingNerfAction(AbstractRelic EvilRelic) {
        duration = Settings.ACTION_DUR_MED;
        botling = EvilRelic;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.getAttacks().size() > 0) {
            AbstractCard c = AbstractDungeon.player.hand.getAttacks().getRandomCard(false);
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(botling, (botling.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(17, 20)] + c.name + ", " + botling.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), 0.0F, 5.0F));
            botling.flash();
            int downTown = AbstractDungeon.cardRandomRng.random(3, 4);
            c.baseDamage -= downTown;
            c.superFlash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, botling));
            c.superFlash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new GrabCardAction(AbstractDungeon.player.getRelic(DeviousBotling.ID)));
        }
        this.isDone = true;
    }
}
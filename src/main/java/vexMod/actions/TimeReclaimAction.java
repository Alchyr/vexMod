package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.relics.FutureThief;

public class TimeReclaimAction extends AbstractGameAction {

    private FutureThief futureThief;

    public TimeReclaimAction(FutureThief fThief) {
        duration = Settings.ACTION_DUR_MED;
        futureThief = fThief;
    }

    @Override
    public void update() {
        for (int i = 0; i < futureThief.stolenCards.size(); i++) {
            AbstractCard d = futureThief.stolenCards.get(i);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID.equals(d.cardID)) {
                    if (AbstractDungeon.cardRandomRng.randomBoolean(0.2F)) {
                        AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(futureThief, (c.name.toUpperCase() + futureThief.DESCRIPTIONS[1]), 0.0F, 5.0F));
                        AbstractDungeon.player.getRelic(FutureThief.ID).flash();
                        AbstractDungeon.player.hand.removeCard(c);
                        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, futureThief));
                        futureThief.stolenCards.remove(futureThief.stolenCards.get(i));
                        i--;
                        break;
                    }
                }
            }
        }
        this.isDone = true;
    }
}
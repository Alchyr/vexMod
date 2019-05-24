package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ActivateExhaustEffectsAction extends AbstractGameAction {
    AbstractCard c;

    public ActivateExhaustEffectsAction(AbstractCard c) {
        this.actionType = ActionType.WAIT;// 13
        this.c = c;// 14
    }// 15

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 18
            this.isDone = true;// 19
        } else {
            Iterator var1 = AbstractDungeon.player.relics.iterator();// 22

            while (var1.hasNext()) {
                AbstractRelic r = (AbstractRelic) var1.next();
                r.onExhaust(this.c);// 23
            }

            var1 = AbstractDungeon.player.powers.iterator();// 25

            while (var1.hasNext()) {
                AbstractPower p = (AbstractPower) var1.next();
                p.onExhaust(this.c);// 26
            }

            this.c.triggerOnExhaust();// 28
            this.isDone = true;// 29
        }
    }// 20 30
}

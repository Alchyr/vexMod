package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.vfx.HolyMoleyGreatBallOfRelics;

public class RelicBallAction extends AbstractGameAction {

    private HolyMoleyGreatBallOfRelics rrfe;

    public RelicBallAction(AbstractCreature target, int amount, boolean vexOnly) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;

        rrfe = new HolyMoleyGreatBallOfRelics(target, vexOnly);
        AbstractDungeon.effectList.add(rrfe);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.target, this.amount, DamageInfo.DamageType.THORNS)));
    }

    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.vfx.RelicRapidFireEffect;

public class RelicRapidFireAction extends AbstractGameAction {

    private RelicRapidFireEffect rrfe;
    public RelicRapidFireAction(AbstractCreature source, int amount) {
        this.source = source;
        this.actionType = ActionType.DAMAGE;

        rrfe = new RelicRapidFireEffect(amount);
        AbstractDungeon.effectList.add(rrfe);
    }

    public void update() {
        if(rrfe.finishedAction)
            this.isDone = true;
    }
}

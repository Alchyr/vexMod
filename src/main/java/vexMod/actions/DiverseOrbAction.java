package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class DiverseOrbAction extends AbstractGameAction {
    private int block;

    public DiverseOrbAction(AbstractPlayer source, int amount, int block) {
        this.setValues(this.target, source, amount);
        this.block = block;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        ArrayList<String> orbList = new ArrayList<String>();

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
            }
        }

        int toDraw = orbList.size() * this.amount;
        for (int i = 0; i < toDraw; i++) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.source, this.source, this.block));
        }

        this.isDone = true;
    }
}

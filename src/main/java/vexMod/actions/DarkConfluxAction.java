package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DarkConfluxAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;

    public DarkConfluxAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {

        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Dark()));

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }


        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(new DarkImpulseAction());
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class MidasTouchAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;

    public MidasTouchAction(AbstractPlayer p, AbstractMonster m, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            AbstractDungeon.player.gainGold(effect * 10);
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < effect * 10; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, true));
                }
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }

        this.isDone = true;
    }
}

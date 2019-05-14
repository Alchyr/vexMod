package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class StabbyXAction extends AbstractGameAction {
    private int damage;
    private int block;
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;

    public StabbyXAction(AbstractPlayer p, AbstractMonster m, int amountdmg, int amountblk, boolean freeToPlayOnce, int energyOnUse) {
        this.damage = amountdmg;
        this.block = amountblk;
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
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
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.p, this.p, this.block));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m, new DamageInfo(this.p, this.damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

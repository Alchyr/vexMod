//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
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
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;
    private int energyOnUse = -1;

    public StabbyXAction(AbstractPlayer p, AbstractMonster m, int amount, boolean freeToPlayOnce, int energyOnUse) {
        this.amount = amount;// 18
        this.p = p;// 19
        this.m = m;// 30
        this.freeToPlayOnce = freeToPlayOnce;// 20
        this.duration = Settings.ACTION_DUR_XFAST;// 21
        this.actionType = ActionType.SPECIAL;// 22
        this.energyOnUse = energyOnUse;// 23
    }// 24

    public void update() {
        int effect = EnergyPanel.totalCount;// 28
        if (this.energyOnUse != -1) {// 29
            effect = this.energyOnUse;// 30
        }

        if (this.p.hasRelic("Chemical X")) {// 33
            effect += 2;// 34
            this.p.getRelic("Chemical X").flash();// 35
        }

        if (effect > 0) {// 38
            for(int i = 0; i < effect; ++i) {// 39
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.p, this.p, this.amount));// 40
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AttackEffect.BLUNT_LIGHT));// 53
            }

            if (!this.freeToPlayOnce) {// 43
                this.p.energy.use(EnergyPanel.totalCount);// 44
            }
        }

        this.isDone = true;// 47
    }// 48
}

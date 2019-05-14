package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DevastationAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature target;
    private DamageInfo.DamageType type;

    public DevastationAction(AbstractPlayer p, AbstractCreature target, DamageInfo.DamageType type) {
        this.p = p;
        this.target = target;
        this.type = type;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, (p.maxHealth - p.currentHealth), type), AbstractGameAction.AttackEffect.FIRE));
        }
        this.tickDuration();
    }
}

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import vexMod.monsters.LichPhylac;

import java.util.Iterator;

public class VampirePhylacAction extends AbstractGameAction {
    private DamageInfo info;

    public VampirePhylacAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.heal(this.info);
            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

    }

    private void heal(DamageInfo info) {
        int healAmount = info.output;
        if (healAmount >= 0) {
            healAmount -= this.target.currentBlock;
            if (healAmount > this.target.currentHealth) {
                healAmount = this.target.currentHealth;
            }

            if (healAmount > 0) {
                if (healAmount > 1 && this.target.hasPower("Buffer")) {
                    return;
                }

                if (healAmount > 1 && this.target.hasPower("IntangiblePlayer")) {
                    healAmount = 1;
                }

                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()) {
                    AbstractMonster m = (AbstractMonster) var3.next();
                    if (m instanceof LichPhylac && !m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToTop(new HealAction(m, this.source, healAmount));
                    }
                }
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }

        }
    }
}

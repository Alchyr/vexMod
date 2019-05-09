//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
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
        this.info = info;// 18
        this.setValues(target, info);// 19
        this.actionType = ActionType.DAMAGE;// 20
        this.attackEffect = effect;// 21
    }// 22

    public void update() {
        if (this.duration == 0.5F) {// 26
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));// 27
        }

        this.tickDuration();// 30
        if (this.isDone) {// 32
            this.heal(this.info);// 33
            this.target.damage(this.info);// 34
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 36
                AbstractDungeon.actionManager.clearPostCombatActions();// 37
            }
        }

    }// 40

    private void heal(DamageInfo info) {
        int healAmount = info.output;// 48
        if (healAmount >= 0) {// 49
            healAmount -= this.target.currentBlock;// 53
            if (healAmount > this.target.currentHealth) {// 55
                healAmount = this.target.currentHealth;// 56
            }

            if (healAmount > 0) {// 59
                if (healAmount > 1 && this.target.hasPower("Buffer")) {// 61
                    return;// 62
                }

                if (healAmount > 1 && this.target.hasPower("IntangiblePlayer")) {// 65
                    healAmount = 1;// 66
                }

                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()) {
                    AbstractMonster m = (AbstractMonster) var3.next();
                    if (m instanceof LichPhylac && !m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToTop(new HealAction(m, this.source, healAmount));// 69
                    }
                }
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));// 70
            }

        }
    }// 50 72
}

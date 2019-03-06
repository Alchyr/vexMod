//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EntryPlanAction extends AbstractGameAction {
    private DamageInfo info;
    private int blockGain;

    public EntryPlanAction(AbstractCreature target, DamageInfo info) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damageType = DamageInfo.DamageType.NORMAL;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
        } else {
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            if (card.type == CardType.ATTACK) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(AbstractDungeon.player, info.base, damageType), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            this.isDone = true;
        }
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EntryPlanAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private AbstractPlayer p;
    private AbstractCreature target;

    public EntryPlanAction(AbstractPlayer p, AbstractCreature target, DamageInfo info) {
        this.p = p;
        this.target = target;
        this.duration = 0.1F;
        this.info = info;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
            } else {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                if (card.type == AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AttackEffect.SLASH_DIAGONAL));
                }

                this.isDone = true;
            }
        }
    }
}

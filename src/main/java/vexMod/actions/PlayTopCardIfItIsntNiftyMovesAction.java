//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.cards.NiftyMoves;

public class PlayTopCardIfItIsntNiftyMovesAction extends AbstractGameAction {
    private boolean exhaustCards;

    public PlayTopCardIfItIsntNiftyMovesAction(AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;// 17
        this.actionType = ActionType.WAIT;// 18
        this.source = AbstractDungeon.player;// 19
        this.target = target;// 20
        this.exhaustCards = exhausts;// 21
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {// 27
                this.isDone = true;// 28
                return;// 29
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {// 32
                AbstractDungeon.actionManager.addToTop(new PlayTopCardIfItIsntNiftyMovesAction(this.target, this.exhaustCards));// 33
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());// 34
                this.isDone = true;// 35
                return;// 36
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {// 39
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();// 40
                if (!(card instanceof NiftyMoves)) {
                    AbstractDungeon.player.drawPile.group.remove(card);// 41
                    AbstractDungeon.getCurrRoom().souls.remove(card);// 42
                    card.freeToPlayOnce = true;// 43
                    card.exhaustOnUseOnce = this.exhaustCards;// 44
                    AbstractDungeon.player.limbo.group.add(card);// 45
                    card.current_y = -200.0F * Settings.scale;// 46
                    card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;// 47
                    card.target_y = (float) Settings.HEIGHT / 2.0F;// 48
                    card.targetAngle = 0.0F;// 49
                    card.lighten(false);// 50
                    card.drawScale = 0.12F;// 51
                    card.targetDrawScale = 0.75F;// 52
                    if (!card.canUse(AbstractDungeon.player, (AbstractMonster) this.target)) {// 54
                        if (this.exhaustCards) {// 55
                            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));// 56
                        } else {
                            AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));// 60
                            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));// 61
                            AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));// 63
                        }
                    } else {
                        card.applyPowers();// 66
                        AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));// 67
                        AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));// 68
                        if (!Settings.FAST_MODE) {// 69
                            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));// 70
                        } else {
                            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));// 72
                        }
                    }
                }

            }

            this.isDone = true;// 76
        }

    }// 78
}

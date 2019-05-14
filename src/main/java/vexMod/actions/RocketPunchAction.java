package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class RocketPunchAction extends AbstractGameAction {
    private static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = uiStrings.TEXT;
    }

    private AbstractPlayer p;

    public RocketPunchAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        Iterator<AbstractCard> var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.group.size() < 1) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            this.p.hand.group.size();
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(this.p.hand.getTopCard().makeStatEquivalentCopy(), 1));
            this.isDone = true;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var1.hasNext()) {
                c = var1.next();
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), 1));
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy(), 1));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }
}

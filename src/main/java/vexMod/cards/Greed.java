package vexMod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.vfx.CoinEffect;
import vexMod.vfx.LoseGoldTextEffect;

import static vexMod.VexMod.makeCardPath;


public class Greed extends AbstractDefaultCard {

    public static final String ID = VexMod.makeID("Greed");
    public static final String IMG = makeCardPath("Greed.png");
    public static final CardColor COLOR = CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    private static final int COST = -2;

    public Greed() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        } else {
            for (int i = 0; i < 6; i++) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CoinEffect(p.hb.cX, p.hb.cY)));
            }
            AbstractDungeon.player.loseGold(6);
            AbstractDungeon.effectList.add(new LoseGoldTextEffect(6));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Greed();
    }


    @Override
    public void upgrade() {
    }
}
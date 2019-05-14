package vexMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;

import static vexMod.VexMod.makeCardPath;


public class UltimateCard extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("UltimateCard");
    public static final String IMG = makeCardPath("UltimateCard.png");
    public static final CardColor COLOR = CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    private static final int COST = 1;


    public UltimateCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return (cardPlayable(m)) && (hasEnoughEnergy());
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        } else {

        }
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new UltimateCard();
    }
}
package vexMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;

import static vexMod.VexMod.makeCardPath;


public class TuneUp extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("TuneUp");
    public static final String IMG = makeCardPath("TuneUp.png");
    public static final CardColor COLOR = CardColor.BLUE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;

    private static final int BLOCK = 3;
    private static final int BLOCK_UP = 1;
    private static final int IMPROVE = 3;
    private static final int IMPROVE_UP = 1;


    public TuneUp() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = IMPROVE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (AbstractDungeon.player.hand.getAttacks().size() > 0) {
            AbstractCard c = AbstractDungeon.player.hand.getAttacks().getRandomCard(false);
            c.baseDamage += magicNumber;
            c.superFlash();
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UP);
            upgradeMagicNumber(IMPROVE_UP);
            initializeDescription();
        }
    }
}
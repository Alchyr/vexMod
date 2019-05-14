package vexMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import vexMod.VexMod;

import java.util.ArrayList;

import static vexMod.VexMod.makeCardPath;


public class Insult extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("Insult");
    public static final String IMG = makeCardPath("Insult.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int InsultES = 2;


    public Insult() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = InsultES;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getTaunt(), 0.0F, 2.0F));
    }

    private String getTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(EXTENDED_DESCRIPTION[0]);
        taunts.add(EXTENDED_DESCRIPTION[1]);
        taunts.add(EXTENDED_DESCRIPTION[2]);
        taunts.add(EXTENDED_DESCRIPTION[3]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}

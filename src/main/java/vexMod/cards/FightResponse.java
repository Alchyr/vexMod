package vexMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;

import java.util.ArrayList;

import static vexMod.VexMod.makeCardPath;


public class FightResponse extends AbstractDefaultCard {
    public static final String ID = VexMod.makeID("FightResponse");
    public static final String IMG = makeCardPath("FightResponse.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int METALLICIZE = 6;
    private static final int METALLICIZE_UP = 6;

    public FightResponse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = METALLICIZE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getTaunt(), 0.0F, 2.0F));
    }

    private String getTaunt() {
        ArrayList<String> taunts = new ArrayList();
        taunts.add(EXTENDED_DESCRIPTION[1]);
        taunts.add(EXTENDED_DESCRIPTION[2]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (AbstractDungeon.player.currentHealth > (Math.floorDiv(AbstractDungeon.player.maxHealth, 2))) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(METALLICIZE_UP);
            initializeDescription();
        }
    }
}
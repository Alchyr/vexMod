package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.actions.RelicBallAction;

import static vexMod.VexMod.makeCardPath;

public class AntiqueFury extends AbstractDefaultCard {

    public static final String ID = VexMod.makeID("AntiqueFury");
    public static final String IMG = makeCardPath("AntiqueFury.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;


    public AntiqueFury() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 0;
        this.exhaust = true;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.relics.size() * 3;

        super.applyPowers();
        if (!this.upgraded) {
            this.rawDescription = DESCRIPTION;
        } else {
            this.rawDescription = UPGRADE_DESCRIPTION;
        }

        this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RelicBallAction(m, false));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
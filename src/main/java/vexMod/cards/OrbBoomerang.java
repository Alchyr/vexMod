package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.PoisonPower;
import vexMod.VexMod;
import vexMod.orbs.GoldenLightning;

import static vexMod.VexMod.makeCardPath;


public class OrbBoomerang extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("OrbBoomerang");
    public static final String IMG = makeCardPath("OrbBoomerang.png");
    public static final CardColor COLOR = CardColor.BLUE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int BLOCK = 6;


    public OrbBoomerang() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (p.maxOrbs > 0) {
            if (!(p.orbs.get(0) instanceof EmptyOrbSlot)) {
                if (p.orbs.get(0) instanceof Lightning || p.orbs.get(0) instanceof GoldenLightning) {
                    for (int i = 0; i < 1; ++i) {
                        AbstractDungeon.player.orbs.get(0).onStartOfTurn();
                        AbstractDungeon.player.orbs.get(0).onEndOfTurn();
                    }
                } else if (p.orbs.get(0) instanceof Frost) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
                } else if (p.orbs.get(0) instanceof Dark) {
                    for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                        if (!monster.isDead && !monster.isDying) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, 3), 3));
                        }
                    }
                } else if (p.orbs.get(0) instanceof Plasma) {
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                } else {
                    for (int i = 0; i < 1; ++i) {
                        AbstractDungeon.player.orbs.get(0).onStartOfTurn();
                        AbstractDungeon.player.orbs.get(0).onEndOfTurn();
                    }
                }
            }
        }
    }

    @Override
    public void applyPowers() {

        super.applyPowers();
        this.rawDescription = EXTENDED_DESCRIPTION[0];

        if (AbstractDungeon.player.maxOrbs > 0) {
            if (AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) {
                this.rawDescription = DESCRIPTION;
            } else if (AbstractDungeon.player.orbs.get(0) instanceof Lightning || AbstractDungeon.player.orbs.get(0) instanceof GoldenLightning) {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[1];
            } else if (AbstractDungeon.player.orbs.get(0) instanceof Frost) {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[2];
            } else if (AbstractDungeon.player.orbs.get(0) instanceof Dark) {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[3];
            } else if (AbstractDungeon.player.orbs.get(0) instanceof Plasma) {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[4];
            } else {
                this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[1];
            }
            this.initializeDescription();
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

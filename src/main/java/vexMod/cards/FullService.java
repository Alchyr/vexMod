package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;

import static vexMod.VexMod.makeCardPath;


public class FullService extends AbstractDefaultCard {

    public static final String ID = VexMod.makeID("FullService");
    public static final String IMG = makeCardPath("FullService.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 3;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 1;
    private static final int GOLD = 5;
    private static final int UPGRADE_PLUS_GOLD = 1;
    private static final int WACK = 2;
    private static final int WACKUP = 1;

    public FullService() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = GOLD;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = WACK;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, magicNumber), magicNumber));
        AbstractDungeon.player.gainGold(this.magicNumber);
        if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
            for (int i = 0; i < this.magicNumber; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(p, m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, true));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber, AbstractGameAction.AttackEffect.POISON));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.defaultSecondMagicNumber), this.defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_GOLD);
            upgradeDefaultSecondMagicNumber(WACKUP);
            initializeDescription();
        }
    }
}
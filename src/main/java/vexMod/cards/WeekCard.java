package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;

import java.util.Date;

import static vexMod.VexMod.makeCardPath;

public class WeekCard extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("WeekCard");
    public static final String IMG = makeCardPath("WeekCardSkill.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    private static final int MONDAY_DAMAGE = 15;
    private static final int TUESDAY_BLOCK = 15;
    private static final int WEDNESDAY_POISON = 7;
    private static final int THURSDAY_BLOCK = 5;
    private static final int THURSDAY_CARDS = 3;
    private static final int FRIDAY_STR = 2;
    private static final int SATURDAY_PLATED_ARMOR = 4;
    private static final int SATURDAY_UPGRADED_PLATED_ARMOR = 5;
    private static final int SUNDAY_DAMAGE = 9;
    private static final int SUNDAY_BLOCK = 9;


    public WeekCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int now = (new Date().getDay());
        String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        String day = strDays[now];
        switch (day) {
            case "Sunday":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case "Monday":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case "Tuesday":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
                break;
            case "Wednesday":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
                break;
            case "Thursday":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
                break;
            case "Friday":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
                break;
            case "Saturday":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
                break;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int now = (new Date().getDay());
        String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        String day = strDays[now];
        switch (day) {
            case "Sunday":
                baseBlock = SUNDAY_BLOCK;
                baseDamage = SUNDAY_DAMAGE;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
                }
                loadCardImage(makeCardPath("WeekCardAttack.png"));
                break;
            case "Monday":
                baseDamage = MONDAY_DAMAGE;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[1];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
                }
                loadCardImage(makeCardPath("WeekCardAttack.png"));
                break;
            case "Tuesday":
                baseBlock = TUESDAY_BLOCK;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[2];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
                }
                loadCardImage(makeCardPath("WeekCardSkill.png"));
                break;
            case "Wednesday":
                baseMagicNumber = magicNumber = WEDNESDAY_POISON;
                this.type = CardType.SKILL;
                this.target = CardTarget.ENEMY;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[3];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[3];
                }
                loadCardImage(makeCardPath("WeekCardSkill.png"));
                break;
            case "Thursday":
                baseBlock = THURSDAY_BLOCK;
                baseMagicNumber = magicNumber = THURSDAY_CARDS;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[4];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[4];
                }
                loadCardImage(makeCardPath("WeekCardSkill.png"));
                break;
            case "Friday":
                baseMagicNumber = magicNumber = FRIDAY_STR;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                if (this.upgraded) {
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[5];
                } else {
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[5];
                }
                loadCardImage(makeCardPath("WeekCardSkill.png"));
                break;
            case "Saturday":
                if (upgraded) {
                    baseMagicNumber = magicNumber = SATURDAY_UPGRADED_PLATED_ARMOR;
                } else {
                    baseMagicNumber = magicNumber = SATURDAY_PLATED_ARMOR;
                }
                this.type = CardType.POWER;
                this.target = CardTarget.SELF;
                this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[6];
                loadCardImage(makeCardPath("WeekCardPower.png"));
                break;
        }
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null) {
            applyPowers();
        }
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
package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;

import java.util.Date;

import static vexMod.VexMod.makeCardPath;

public class WeekCard extends AbstractDefaultCard {

    // TEXT DECLARATION

    public static final String ID = VexMod.makeID("WeekCard"); // VexMod.makeID("${NAME}");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("WeekCard.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;  // COST = ${COST}

    private static final int MONDAY_DAMAGE = 15;
    private static final int TUESDAY_BLOCK = 15;
    private static final int WEDNESDAY_POISON = 7;
    private static final int THURSDAY_BLOCK = 5;
    private static final int THURSDAY_CARDS = 3;
    private static final int FRIDAY_STR = 2;
    private static final int SATURDAY_HEAL = 6;
    private static final int SUNDAY_DAMAGE = 9;
    private static final int SUNDAY_BLOCK = 9;

    // /STAT DECLARATION/


    public WeekCard() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        int now = (new Date().getDay());
    }


    // Actions the card should do.
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
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));// 37
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));// 38
                break;
            case "Friday":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));// 41
                break;
            case "Saturday":
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));// 40
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
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
                break;
            case "Monday":
                baseDamage = MONDAY_DAMAGE;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
                break;
            case "Tuesday":
                baseBlock = TUESDAY_BLOCK;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
                break;
            case "Wednesday":
                baseMagicNumber = magicNumber = WEDNESDAY_POISON;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[3];
                break;
            case "Thursday":
                baseBlock = THURSDAY_BLOCK;
                baseMagicNumber = magicNumber = THURSDAY_CARDS;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[4];
                break;
            case "Friday":
                baseMagicNumber = magicNumber = FRIDAY_STR;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[5];
                break;
            case "Saturday":
                baseMagicNumber = magicNumber = SATURDAY_HEAL;
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[6];
                break;

        }
        this.initializeDescription();
    }

    // Upgraded stats.
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
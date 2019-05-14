package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import vexMod.VexMod;

import java.util.Iterator;

import static vexMod.VexMod.makeCardPath;

public class VolumeVengeance extends AbstractDefaultCard {


    public static final String ID = VexMod.makeID("VolumeVengeance");
    public static final String IMG = makeCardPath("VolumeVengeanceSkill.png");
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
    private static final int UPGRADED_COST = 0;

    private static final int QUIET_DAMAGE = 17;
    private static final int LOUD_DEBUFF = 9;


    public VolumeVengeance() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null) {
            applyPowers();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float vol = (Settings.MASTER_VOLUME);
        if (vol <= 0.2F) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        } else if (vol >= 0.8F) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_PIERCING_WAIL"));
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
            }

            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            AbstractMonster mo;
            while (var3.hasNext()) {
                mo = (AbstractMonster) var3.next();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }

            var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var3.hasNext()) {
                mo = (AbstractMonster) var3.next();
                if (!mo.hasPower("Artifact")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        float vol = (Settings.MASTER_VOLUME);
        if (vol <= 0.2F) {
            baseDamage = QUIET_DAMAGE;
            this.type = CardType.ATTACK;
            this.target = CardTarget.ENEMY;
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            loadCardImage(makeCardPath("VolumeVengeanceAttack.png"));
        } else if (vol >= 0.8F) {
            baseMagicNumber = magicNumber = LOUD_DEBUFF;
            this.type = CardType.SKILL;
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
            loadCardImage(makeCardPath("VolumeVengeanceSkill.png"));
        } else {
            this.type = CardType.SKILL;
            this.target = CardTarget.SELF;
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
            loadCardImage(makeCardPath("VolumeVengeanceSkill.png"));
        }
        this.initializeDescription();
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
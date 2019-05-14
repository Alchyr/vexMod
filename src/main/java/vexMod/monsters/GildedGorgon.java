package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import vexMod.VexMod;
import vexMod.powers.MedusaPower;
import vexMod.powers.StoneSkinPower;

public class GildedGorgon extends AbstractMonster {
    public static final String ID = VexMod.makeID("GildedGorgon");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 177;
    private static final int HP_MAX = 200;
    private static final int A_7_HP_MIN = 250;
    private static final int A_7_HP_MAX = 300;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACKVOID_DAMAGE = 17;
    private static final int A_2_ATTACKVOID_DAMAGE = 23;
    private static final int DEBUFF_AMT = 8;
    private static final int A_17_DEBUFF_AMT = 15;
    private int debuffAmt;
    private boolean firstTurn = true;

    public GildedGorgon(float x, float y) {
        super(NAME, "GildedGorgon", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/GildedGorgon.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackVoidDmg;
        if (AbstractDungeon.ascensionLevel >= 17) {
            attackVoidDmg = A_2_ATTACKVOID_DAMAGE;
            this.debuffAmt = A_17_DEBUFF_AMT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            attackVoidDmg = A_2_ATTACKVOID_DAMAGE;
            this.debuffAmt = DEBUFF_AMT;
        } else {
            attackVoidDmg = ATTACKVOID_DAMAGE;
            this.debuffAmt = DEBUFF_AMT;
        }

        this.damage.add(new DamageInfo(this, attackVoidDmg));
    }

    public void usePreBattleAction() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StoneSkinPower(this, this, 3), 3));

    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
                break;
            case 2:

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, debuffAmt), debuffAmt));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new MedusaPower(AbstractDungeon.player, this, 1), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.firstTurn) {
            this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            this.firstTurn = false;
        } else {
            if ((AbstractDungeon.aiRng.randomBoolean(0.6F))) {
                this.setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
            } else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
        }
    }

    public void die() {
        super.die();

    }

}

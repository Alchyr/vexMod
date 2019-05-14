package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThieveryPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import vexMod.VexMod;

public class EvilSsserpent extends AbstractMonster {
    public static final String ID = VexMod.makeID("EvilSsserpent");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 222;
    private static final int HP_MAX = 222;
    private static final int A_7_HP_MIN = 333;
    private static final int A_7_HP_MAX = 333;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int BLOCK_BLOCK = 35;
    private static final int A_2_BLOCK_BLOCK = 50;
    private static final int DEBUFF_AMT = 10;
    private static final int A_2_DEBUFF_AMT = 15;
    private int debuffAmt;
    private boolean firstTurn = true;
    private int stolenGold = 0;

    public EvilSsserpent(float x, float y) {
        super(NAME, "EvilSsserpent", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/EvilSsserpent.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackBlockBlock;
        if (AbstractDungeon.ascensionLevel >= 17) {
            attackBlockBlock = A_2_BLOCK_BLOCK;
            this.debuffAmt = A_2_DEBUFF_AMT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            attackBlockBlock = A_2_BLOCK_BLOCK;
            this.debuffAmt = A_2_DEBUFF_AMT;
        } else {
            attackBlockBlock = BLOCK_BLOCK;
            this.debuffAmt = DEBUFF_AMT;
        }

        this.damage.add(new DamageInfo(this, attackBlockBlock));

    }

    public void usePreBattleAction() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThieveryPower(this, 100)));

    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), 100));
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    public void update() {
                        EvilSsserpent.this.stolenGold += Math.min(100, AbstractDungeon.player.gold);
                        this.isDone = true;
                    }
                });
                break;
            case 2:

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, debuffAmt), debuffAmt));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Doubt().makeCopy()));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.lastMove((byte) 2)) {
            this.setMove((byte) 3, Intent.STRONG_DEBUFF);
        } else {
            if ((this.lastMove((byte) 3)) || this.firstTurn) {
                this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
            } else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
        }
    }

    public void die() {
        if (this.stolenGold > 0) {
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(this.stolenGold);
        }
        super.die();

    }

}

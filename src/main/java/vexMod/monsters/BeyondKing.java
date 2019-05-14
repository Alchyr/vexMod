package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import vexMod.VexMod;
import vexMod.powers.*;

import java.util.ArrayList;

public class BeyondKing extends AbstractMonster {
    public static final String ID = VexMod.makeID("BeyondKing");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 800;
    private static final int HP_MAX = 800;
    private static final int A_9_HP_MIN = 880;
    private static final int A_9_HP_MAX = 880;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 276.0F;
    private static final float HB_H = 280.0F;
    private static final int BLOCK_GAIN = 15;
    private static final int ASC_4_BLOCK_GAIN = 19;
    private static final int DECREE_DAMAGE = 14;
    private static final int ASC_4_DECREE_DAMAGE = 20;
    private static final int BIG_DEBUFF_AMOUNT = 1;
    private static final int ASC_19_BIG_DEBUFF_AMOUNT = 2;
    private int block_gain;
    private int big_debuff_amt;
    private boolean firstTurn = true;

    public BeyondKing(float x, float y) {
        super(NAME, "BeyondKing", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/BeyondKing.png", x, y);

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int decree_damage;
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.block_gain = ASC_4_BLOCK_GAIN;
            decree_damage = ASC_4_DECREE_DAMAGE;
            this.big_debuff_amt = ASC_19_BIG_DEBUFF_AMOUNT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.block_gain = ASC_4_BLOCK_GAIN;
            decree_damage = ASC_4_DECREE_DAMAGE;
            this.big_debuff_amt = BIG_DEBUFF_AMOUNT;
        } else {
            this.block_gain = BLOCK_GAIN;
            decree_damage = DECREE_DAMAGE;
            this.big_debuff_amt = BIG_DEBUFF_AMOUNT;
        }

        this.damage.add(new DamageInfo(this, decree_damage));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SlowDoomPower(AbstractDungeon.player, this, 5), 5));
    }

    private AbstractPower getRandomLetterCurse(int amount) {
        AbstractPower CursePower;
        ArrayList<AbstractPower> curseList = new ArrayList<>();
        curseList.add(new CursedAPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedBPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedCPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedDPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedEPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedFPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedGPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedHPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedIPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedJPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedKPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedLPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedMPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedNPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedOPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedPPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedQPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedRPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedSPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedTPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedUPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedVPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedWPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedXPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedYPower(AbstractDungeon.player, this, amount));
        curseList.add(new CursedZPower(AbstractDungeon.player, this, amount));

        CursePower = curseList.get(AbstractDungeon.cardRandomRng.random(0, (curseList.size() - 1)));

        return CursePower;
    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, getRandomLetterCurse(this.big_debuff_amt), this.big_debuff_amt));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, getRandomLetterCurse(this.big_debuff_amt), this.big_debuff_amt));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, getRandomLetterCurse(1), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true)));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, getRandomLetterCurse(1), 1));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.block_gain));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, getRandomLetterCurse(1), 1));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.SMASH));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        ArrayList<Integer> wahoo = new ArrayList<>();
        wahoo.add(0);
        wahoo.add(1);
        wahoo.add(2);
        wahoo.add(3);
        if (this.lastMove((byte) 1)) {
            wahoo.remove(0);
        }
        if (this.lastMove((byte) 2)) {
            wahoo.remove(1);
        }
        if (this.lastMove((byte) 3)) {
            wahoo.remove(2);
        }
        if (this.lastMove((byte) 4)) {
            wahoo.remove(3);
        }
        int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size() - 1));
        if (waaa == 0) {
            this.setMove((byte) 1, Intent.STRONG_DEBUFF);
        } else if (waaa == 1) {
            this.setMove((byte) 2, Intent.DEBUFF);
        } else if (waaa == 2) {
            this.setMove((byte) 3, Intent.DEFEND_DEBUFF);
        } else if (waaa == 3) {
            this.setMove((byte) 4, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            super.die();
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
        }
    }

}

package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import vexMod.VexMod;
import vexMod.powers.*;

import java.util.ArrayList;

public class BeyondKing extends AbstractMonster {
    public static final String ID = VexMod.makeID("BeyondKing"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 800; // Always good to back up your health and move values.
    private static final int HP_MAX = 800;
    private static final int A_9_HP_MIN = 880; // HP moves up at Ascension 7.
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
    private int decree_damage;
    private int big_debuff_amt;
    private static final byte DOUBLECURSE = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte WEAKCURSE = 2;
    private static final byte BLOCKCURSE = 3;
    private static final byte ATTACKTIME = 4;
    private boolean firstTurn = true;

    public BeyondKing(float x, float y) {
        super(NAME, "BeyondKing", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/BeyondKing.png", x, y); // Initializes the monster.

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) { // Checks if your Ascension is 7 or above...
            this.setHp(A_9_HP_MIN, A_9_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.block_gain = ASC_4_BLOCK_GAIN;
            this.decree_damage = ASC_4_DECREE_DAMAGE;
            this.big_debuff_amt = ASC_19_BIG_DEBUFF_AMOUNT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.block_gain = ASC_4_BLOCK_GAIN;
            this.decree_damage = ASC_4_DECREE_DAMAGE;
            this.big_debuff_amt = BIG_DEBUFF_AMOUNT;
        } else {
            this.block_gain = BLOCK_GAIN;
            this.decree_damage = DECREE_DAMAGE;
            this.big_debuff_amt = BIG_DEBUFF_AMOUNT;
        }

        this.damage.add(new DamageInfo(this, this.decree_damage));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();// 97
        AbstractDungeon.scene.fadeOutAmbiance();// 98
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");// 99
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
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
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
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.SMASH));// 93 94
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
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

    public void die() { // When this monster dies...
        if (!AbstractDungeon.getCurrRoom().cannotLose) {// 240
            this.useFastShakeAnimation(5.0F);// 241
            CardCrawlGame.screenShake.rumble(4.0F);// 242
            super.die();// 243
            this.onBossVictoryLogic();// 244
            this.onFinalBossVictoryLogic();// 247
        }
    }

}// You made it! End of monster.

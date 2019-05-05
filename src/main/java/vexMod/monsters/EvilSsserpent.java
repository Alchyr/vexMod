package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.Looter;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import vexMod.VexMod;
import vexMod.powers.InkPower;

public class EvilSsserpent extends AbstractMonster {
    public static final String ID = VexMod.makeID("EvilSsserpent"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 222; // Always good to back up your health and move values.
    private static final int HP_MAX = 222;
    private static final int A_7_HP_MIN = 333; // HP moves up at Ascension 7.
    private static final int A_7_HP_MAX = 333;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int BLOCK_BLOCK = 35;
    private static final int A_2_BLOCK_BLOCK = 50;
    private static final int DEBUFF_AMT = 10;
    private static final int A_2_DEBUFF_AMT = 15;
    private int attackBlockBlock;
    private int debuffAmt;
    private static final byte ATTACKBLOCK = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte WEAKEN = 2;
    private static final byte DEBUFFDRAW = 3;
    private boolean firstTurn = true;
    private int stolenGold = 0;

    public EvilSsserpent(float x, float y) {
        super(NAME, "EvilSsserpent", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/hex.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 7) { // Checks if your Ascension is 7 or above...
            this.setHp(A_7_HP_MIN, A_7_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.attackBlockBlock = A_2_BLOCK_BLOCK;
            this.debuffAmt = A_2_DEBUFF_AMT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.attackBlockBlock = A_2_BLOCK_BLOCK;
            this.debuffAmt = A_2_DEBUFF_AMT;
        } else {
            this.attackBlockBlock = BLOCK_BLOCK;
            this.debuffAmt = DEBUFF_AMT;
        }

        this.damage.add(new DamageInfo(this, this.attackBlockBlock)); // Creates a damageInfo for each attack.
        // Creates a damageInfo for each attack.
    }

    public void usePreBattleAction() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThieveryPower(this, 100)));

    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F)); // Plays visual effects for attack.
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), 100)); // Deals the big damage.
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {// 97
                    public void update() {
                        EvilSsserpent.this.stolenGold += Math.min(100, AbstractDungeon.player.gold);// 101
                        this.isDone = true;// 102
                    }// 103
                });
                break;
            case 2:
                // AbstractDungeon.actionManager.addToBottom(new SFXAction("SOTE_SFX_POTION_1_v2")); // Plays a sound.
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, debuffAmt), debuffAmt));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Doubt().makeCopy()));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (this.lastMove((byte) 2)) { // Checks if the last two attacks were not megadebuff.
            this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the monster's intent to megadebuff if so.
        } else {
            if ((this.lastMove((byte) 3)) || this.firstTurn) {
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base); // Sets the move to regular attack if the last move wasn't regular attack.
            } else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
        }
    }

    public void die() { // When this monster dies...
        if (this.stolenGold > 0) {// 185
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(this.stolenGold);// 186
        }
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

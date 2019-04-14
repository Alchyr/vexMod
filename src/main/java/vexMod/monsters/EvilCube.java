//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import vexMod.VexMod;
import vexMod.powers.EnemyBufferPower;
import vexMod.powers.ForceCubePower;

public class EvilCube extends AbstractMonster {
    public static final String ID = VexMod.makeID("EvilCube"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 80; // Always good to back up your health and move values.
    private static final int HP_MAX = 92;
    private static final int A_7_HP_MIN = 53; // HP moves up at Ascension 7.
    private static final int A_7_HP_MAX = 59;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACK1_DMG = 12;
    private static final int A_2_ATTACK1_DMG = 17; // Some values change on Ascension 2...
    private static final int ATTACKEAT_DMG = 10;
    private static final int DRAWDOWN_AMT = 1;
    private static final int A_17_DRAWDOWN_AMT = 2;
    private int attack1Dmg;
    private int attackEatDmg;
    private int drawDownAmt;
    private static final byte ATTACK1 = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte ATTACKEAT = 2;
    private static final byte DEBUFFDRAW = 3;
    private boolean firstTurn = true;

    public EvilCube(float x, float y) {
        super(NAME, "EvilCube", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/hex.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 7) { // Checks if your Ascension is 7 or above...
            this.setHp(A_7_HP_MIN, A_7_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.attack1Dmg = A_2_ATTACK1_DMG;
            this.attackEatDmg = ATTACKEAT_DMG;
            this.drawDownAmt = A_17_DRAWDOWN_AMT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.attack1Dmg = A_2_ATTACK1_DMG;
            this.attackEatDmg = ATTACKEAT_DMG;
            this.drawDownAmt = DRAWDOWN_AMT;
        } else {
            this.attack1Dmg = ATTACK1_DMG;
            this.attackEatDmg = ATTACKEAT_DMG;
            this.drawDownAmt = DRAWDOWN_AMT;
        }

        this.damage.add(new DamageInfo(this, this.attack1Dmg)); // Creates a damageInfo for each attack.
        this.damage.add(new DamageInfo(this, this.attackEatDmg));
    }

    public void usePreBattleAction() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ForceCubePower(this, this, 1),1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new EnemyBufferPower(this, 3)));
    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F)); // Plays visual effects for attack.
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.NONE)); // Deals the big damage.
                break;
            case 2:
               // AbstractDungeon.actionManager.addToBottom(new SFXAction("SOTE_SFX_POTION_1_v2")); // Plays a sound.
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.NONE)); // Deals the big damage.
                AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(this));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, this.drawDownAmt), this.drawDownAmt));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (num < 25) { // Checks if the number is below 25.
            if (this.lastMove((byte) 1)) { // Checks if the last attack was a regular attack.
                if (AbstractDungeon.aiRng.randomBoolean(0.5625F)) { // Checks a random chance, in this case a little more than 1/2.
                    this.setMove((byte) 2, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base); // Sets the monster's intent to the buff if the chance goes through.
                } else {
                    this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the monster's intent to the attack and block if the chance fails.
                }
            } else {
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base); // Sets the move to regular attack if the last move wasn't regular attack.
            }
        } else if (num < 55) { // checks if the number is below 55.
            if (this.lastTwoMoves((byte) 3)) { // Checks if the last two moves were both attack and block.
                if (AbstractDungeon.aiRng.randomBoolean(0.357F)) { // Checks a random chance. This case is around 35%.
                    this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base); // Sets the monster's intent to the regular attack.
                } else {
                    this.setMove((byte) 2, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base); // Sets the monster's intent to the defensive buff.
                }
            } else {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the intent to attack and block.
            }
        } else if (this.lastMove((byte) 2)) { // Checks if the last move was the buff.
            if (AbstractDungeon.aiRng.randomBoolean(0.416F)) { // Checks a random boolean - in this case slightly less than 1/2.
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base); // Sets the intent to the regular attack.
            } else {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF); // Sets the intent to the attaak and block.
            }
        } else {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base); // If none of the above things were done, sets the intent to the defensive buff.
        }

    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

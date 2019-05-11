package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
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

import java.util.ArrayList;

public class BombBelcher extends AbstractMonster {
    public static final String ID = VexMod.makeID("BombBelcher"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 175; // Always good to back up your health and move values.
    private static final int HP_MAX = 180;
    private static final int A_8_HP_MIN = 185; // HP moves up at Ascension 7.
    private static final int A_8_HP_MAX = 195;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACK_BLOCK_DAMAGE = 14;
    private static final int ATTACK_BLOCK_BLOCK = 14;
    private static final int BIG_ATTACK_DAMAGE = 16;
    private static final int FRAIL_ATTACK_DAMAGE = 12;
    private static final int A_3_ATTACK_BLOCK_DAMAGE = 16;
    private static final int A_3_ATTACK_BLOCK_BLOCK = 16;
    private static final int A_3_BIG_ATTACK_DAMAGE = 20;
    private static final int A_3_FRAIL_ATTACK_DAMAGE = 15;
    private int attackBlockBlock;
    private int attackBlockDamage;
    private int bigAttackDamage;
    private int frailAttackDamage;
    private static final byte ATTACKBLOCK = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte BIGATTACK = 2;
    private static final byte FRAILATTACK = 3;
    private boolean firstTurn = true;

    public BombBelcher(float x, float y) {
        super(NAME, "BombBelcher", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/BombBelcher.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 8) { // Checks if your Ascension is 7 or above...
            this.setHp(A_8_HP_MIN, A_8_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackBlockBlock = A_3_ATTACK_BLOCK_BLOCK;
            this.attackBlockDamage = A_3_ATTACK_BLOCK_DAMAGE;
            this.bigAttackDamage = A_3_BIG_ATTACK_DAMAGE;
            this.frailAttackDamage = A_3_FRAIL_ATTACK_DAMAGE;
        } else {
            this.attackBlockBlock = ATTACK_BLOCK_BLOCK;
            this.attackBlockDamage = ATTACK_BLOCK_DAMAGE;
            this.bigAttackDamage = BIG_ATTACK_DAMAGE;
            this.frailAttackDamage = FRAIL_ATTACK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, this.attackBlockDamage)); // Creates a damageInfo for each attack.
        this.damage.add(new DamageInfo(this, this.bigAttackDamage));
        this.damage.add(new DamageInfo(this, this.frailAttackDamage));
        // Creates a damageInfo for each attack.
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 10), 10));
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 6), 6));// 122
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 3), 3));// 122
        }
    }

    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.BLUNT_LIGHT));// 93 94
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                this.setMove((byte) 2, Intent.ATTACK_BUFF, ((DamageInfo) this.damage.get(1)).base);
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.BLUNT_HEAVY));// 93 94
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                this.setMove((byte) 3, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(2)).base);
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.FIRE));// 93 94
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                this.setMove((byte) 2, Intent.ATTACK_DEFEND, ((DamageInfo) this.damage.get(0)).base);
        }
    }

    protected void getMove(int num) { // Gets a number for movement
        this.setMove((byte) 2, Intent.ATTACK_BUFF, ((DamageInfo) this.damage.get(1)).base);// 111
    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

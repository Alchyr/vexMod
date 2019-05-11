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

public class Combatant extends AbstractMonster {
    public static final String ID = VexMod.makeID("Combatant"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 160; // Always good to back up your health and move values.
    private static final int HP_MAX = 170;
    private static final int A_8_HP_MIN = 180; // HP moves up at Ascension 7.
    private static final int A_8_HP_MAX = 190;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACK_BLOCK_DAMAGE = 12;
    private static final int ATTACK_BLOCK_BLOCK = 12;
    private static final int BIG_ATTACK_DAMAGE = 16;
    private static final int SELF_HEAL_ATTACK_DAMAGE = 10;
    private static final int A_3_ATTACK_BLOCK_DAMAGE = 15;
    private static final int A_3_ATTACK_BLOCK_BLOCK = 15;
    private static final int A_3_BIG_ATTACK_DAMAGE = 19;
    private static final int A_3_SELF_HEAL_ATTACK_DAMAGE = 13;
    private int attackBlockBlock;
    private int attackBlockDamage;
    private int bigAttackDamage;
    private int selfHealAttackDamage;
    private static final byte ATTACKBLOCK = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte BIGATTACK = 2;
    private static final byte HEALATTACK = 3;
    private static final byte BIGDEBUFF = 4;
    private boolean firstTurn = true;

    public Combatant(float x, float y) {
        super(NAME, "Combatant", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/Combatant.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 8) { // Checks if your Ascension is 7 or above...
            this.setHp(A_8_HP_MIN, A_8_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackBlockBlock = A_3_ATTACK_BLOCK_BLOCK;
            this.attackBlockDamage = A_3_ATTACK_BLOCK_DAMAGE;
            this.bigAttackDamage = A_3_BIG_ATTACK_DAMAGE;
            this.selfHealAttackDamage = A_3_SELF_HEAL_ATTACK_DAMAGE;
        } else {
            this.attackBlockBlock = ATTACK_BLOCK_BLOCK;
            this.attackBlockDamage = ATTACK_BLOCK_DAMAGE;
            this.bigAttackDamage = BIG_ATTACK_DAMAGE;
            this.selfHealAttackDamage = SELF_HEAL_ATTACK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, this.attackBlockDamage)); // Creates a damageInfo for each attack.
        this.damage.add(new DamageInfo(this, this.bigAttackDamage));
        this.damage.add(new DamageInfo(this, this.selfHealAttackDamage));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 3), 3));// 122
        }
    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.BLUNT_HEAVY));// 93 94
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.SLASH_DIAGONAL));// 93 94
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.SMASH));// 93 94
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 5));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (GameActionManager.turn == 3) {
            this.setMove((byte) 4, Intent.STRONG_DEBUFF);
        } else {
            ArrayList<Integer> wahoo = new ArrayList<>();
            wahoo.add(0);
            wahoo.add(1);
            wahoo.add(2);
            if (this.lastMove((byte) 1)) {
                wahoo.remove(0);
            }
            if (this.lastMove((byte) 2)) {
                wahoo.remove(1);
            }
            if (this.lastMove((byte) 3)) {
                wahoo.remove(2);
            }
            int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size() - 1));
            if (waaa == 0) {
                this.setMove((byte) 1, Intent.ATTACK_DEFEND, this.damage.get(0).base);
            } else if (waaa == 1) {
                this.setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base);
            } else if (waaa == 2) {
                this.setMove((byte) 3, Intent.ATTACK_BUFF, this.damage.get(2).base);
            }
        }
    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

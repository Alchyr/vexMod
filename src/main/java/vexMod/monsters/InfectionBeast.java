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
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
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
import vexMod.cards.Virus;
import vexMod.powers.InkPower;

import java.util.ArrayList;

public class InfectionBeast extends AbstractMonster {
    public static final String ID = VexMod.makeID("InfectionBeast"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 210; // Always good to back up your health and move values.
    private static final int HP_MAX = 220;
    private static final int A_8_HP_MIN = 230; // HP moves up at Ascension 7.
    private static final int A_8_HP_MAX = 240;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACK_LIFESTEAL_DAMAGE = 15;
    private static final int A_3_ATTACK_LIFESTEAL_DAMAGE = 20;
    private int attackLifestealDamage;
    private static final byte ATTACKLIFESTEAL = 1;
    private static final byte DEBUFFTIME = 2;
    private static final byte INFECTION = 3;
    private boolean firstTurn = true;

    public InfectionBeast(float x, float y) {
        super(NAME, "InfectionBeast", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/InfectionBeast.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 8) { // Checks if your Ascension is 7 or above...
            this.setHp(A_8_HP_MIN, A_8_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackLifestealDamage = A_3_ATTACK_LIFESTEAL_DAMAGE;
        } else {
            this.attackLifestealDamage = ATTACK_LIFESTEAL_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, this.attackLifestealDamage));
        // Creates a damageInfo for each attack.
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 6), 6));// 122
        }
    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.POISON));// 93 94
                break;
            case 2:
                if (!AbstractDungeon.player.hasPower(ConstrictedPower.POWER_ID)) {
                    int letsSee = AbstractDungeon.monsterRng.random(3);
                    if (letsSee == 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 1));
                    } else if (letsSee == 1) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));// 154
                    } else if (letsSee == 2) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 1));
                    } else if (letsSee == 3) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 5), 5));
                    }
                } else {
                    int letsSee = AbstractDungeon.monsterRng.random(2);
                    if (letsSee == 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 1));
                    } else if (letsSee == 1) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));// 154
                    } else if (letsSee == 2) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 1));
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(), 2, true, true));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (this.firstTurn) {
            this.setMove((byte) 3, Intent.STRONG_DEBUFF);
        } else {
            if (this.lastMove((byte) 2)) {
                this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
            } else {
                this.setMove((byte) 2, Intent.STRONG_DEBUFF);
            }
        }
    }


    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

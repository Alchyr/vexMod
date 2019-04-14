package vexMod.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import vexMod.VexMod;
import vexMod.cards.Virus;
import vexMod.powers.InkPower;
import vexMod.powers.MedusaPower;

public class InfectionBeast extends AbstractMonster {
    public static final String ID = VexMod.makeID("InfectionBeast"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 140; // Always good to back up your health and move values.
    private static final int HP_MAX = 163;
    private static final int A_7_HP_MIN = 170; // HP moves up at Ascension 7.
    private static final int A_7_HP_MAX = 192;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int LIFESTEAL_DAMAGE = 14;
    private static final int A_2_LIFESTEAL_DAMAGE = 18;
    private static final int VIRUS_AMT = 1;
    private static final int A_17_VIRUS_AMT = 2;
    private int attackVirusDmg;
    private int debuffAmt;
    private static final byte LIFESTEAL = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte WEAKEN = 2;
    private static final byte PERMACURSE = 3;
    private boolean firstTurn = true;
    private int COUNTDOWN = 0;

    public InfectionBeast(float x, float y) {
        super(NAME, "InfectionBeast", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/hex.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 7) { // Checks if your Ascension is 7 or above...
            this.setHp(A_7_HP_MIN, A_7_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.attackVirusDmg = A_2_LIFESTEAL_DAMAGE;
            this.debuffAmt = A_17_VIRUS_AMT;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.attackVirusDmg = A_2_LIFESTEAL_DAMAGE;
            this.debuffAmt = VIRUS_AMT;
        } else {
            this.attackVirusDmg = LIFESTEAL_DAMAGE;
            this.debuffAmt = VIRUS_AMT;
        }

        this.damage.add(new DamageInfo(this, this.attackVirusDmg)); // Creates a damageInfo for each attack.
    }

    // public void usePreBattleAction() {

    //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ForceCubePower(this, this, 1),1));

    // }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0F, 25.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale, Color.GREEN.cpy()), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.NONE));
                break;
            case 2:
                // AbstractDungeon.actionManager.addToBottom(new SFXAction("SOTE_SFX_POTION_1_v2")); // Plays a sound.
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(), debuffAmt, true, true));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(this, 0.5F, 0.2F));
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(CardLibrary.getCard("vexMod:Virus").makeCopy()));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (this.COUNTDOWN == 5) {
            this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            this.COUNTDOWN = 0;
        } else {
            if (this.COUNTDOWN!=1 && this.COUNTDOWN !=4) {
                this.setMove((byte) 1, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(0)).base); // Sets the move to regular attack if the last move wasn't regular attack.
            } else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
            this.COUNTDOWN += 1;
        }
    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

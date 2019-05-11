package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import vexMod.VexMod;
import vexMod.powers.GriftingPower;
import vexMod.powers.InkPower;
import vexMod.powers.MedusaPower;
import vexMod.powers.StoneSkinPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grifter extends AbstractMonster {
    public static final String ID = VexMod.makeID("Grifter"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 152; // Always good to back up your health and move values.
    private static final int HP_MAX = 152;
    private static final int A_7_HP_MIN = 160; // HP moves up at Ascension 7.
    private static final int A_7_HP_MAX = 160;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACKWEAK_DAMAGE = 16;
    private static final int ATTACKBLOCK_DAMAGE = 18;
    private static final int ATTACKBLOCK_BLOCK = 14;
    private static final int RECKLESS_DAMAGE = 19;
    private static final int DEFEND_BLOCK = 16;
    private int attackWeakDmg;
    private int attackBlockDmg;
    private int recklessDmg;
    private int attackBlockBlock;
    private int defendBlock;
    private static final byte ATTACKWEAK = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte ATTACKBLOCK = 2;
    private static final byte RECKLESS = 3;
    private static final byte DEFEND = 4;
    private static final byte ESCAPE = 5;
    private boolean firstTurn;
    private int moveAmt;

    public Grifter(float x, float y) {
        super(NAME, "Grifter", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/Grifter.png", x, y); // Initializes the monster.

        if (AbstractDungeon.ascensionLevel >= 7) { // Checks if your Ascension is 7 or above...
            this.setHp(A_7_HP_MIN, A_7_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.attackWeakDmg = ATTACKWEAK_DAMAGE;
            this.attackBlockDmg = ATTACKBLOCK_DAMAGE;
            this.recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.attackWeakDmg = ATTACKWEAK_DAMAGE;
            this.attackBlockDmg = ATTACKBLOCK_DAMAGE;
            this.recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        } else {
            this.attackWeakDmg = ATTACKWEAK_DAMAGE;
            this.attackBlockDmg = ATTACKBLOCK_DAMAGE;
            this.recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        }

        this.damage.add(new DamageInfo(this, this.attackWeakDmg));
        this.damage.add(new DamageInfo(this, this.attackBlockDmg));
        this.damage.add(new DamageInfo(this, this.recklessDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GriftingPower(this, this, 1)));
    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }

        switch (this.nextMove) {
            case 1:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList();
                    relics.addAll(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 2:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList();
                    relics.addAll(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, attackBlockBlock));
                break;
            case 3:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList();
                    relics.addAll(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, defendBlock));
                break;
            case 5:
                AbstractDungeon.getCurrRoom().rewards.removeIf(rewardItem -> rewardItem.type == RewardItem.RewardType.RELIC);
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.3F, 2.5F));// 143
                AbstractDungeon.getCurrRoom().mugged = true;// 144
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(this.hb.cX, this.hb.cY)));// 145
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));// 146
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 5, Intent.ESCAPE));// 147
                break;// 148
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement
        moveAmt++;
        if (moveAmt == 1) {
            this.setMove((byte) 1, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(0)).base);// 111
        } else if (moveAmt == 2) {
            this.setMove((byte) 2, Intent.ATTACK_DEFEND, ((DamageInfo) this.damage.get(1)).base);// 111
        } else if (moveAmt == 3) {
            this.setMove((byte) 3, Intent.ATTACK_DEFEND, ((DamageInfo) this.damage.get(2)).base);// 111
        } else if (moveAmt == 4) {
            this.setMove((byte) 4, Intent.DEFEND);// 111
        } else if (moveAmt == 5) {
            this.setMove((byte) 5, Intent.ESCAPE);// 111
        }
    }

    public void die() { // When this monster dies...
        super.die(); // It, uh, dies...
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

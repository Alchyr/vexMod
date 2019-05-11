package vexMod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import vexMod.VexMod;
import vexMod.actions.VampirePhylacAction;
import vexMod.powers.*;

import java.util.ArrayList;
import java.util.Iterator;

public class LichLord extends AbstractMonster {
    public static final String ID = VexMod.makeID("LichLord"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 135; // Always good to back up your health and move values.
    private static final int HP_MAX = 135;
    private static final int A_9_HP_MIN = 150; // HP moves up at Ascension 7.
    private static final int A_9_HP_MAX = 150;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 379.0F;
    private static final float HB_H = 434.0F;
    private static final int DAMAGE_BLAAAM = 21;
    private static final int ASC_4_DAMAGE_BLAAAM = 25;
    private static final int DAMAGE_LIFESTEAL = 18;
    private static final int ASC_4_DAMAGE_LIFESTEAL = 22;
    private static final int DISCARD_DAMAGE = 15; // actually it's now apply 2 poison
    private static final int ASC_4_DISCARD_DAMAGE = 18; // actually it's now apply 2 poison
    private int damageBlaaam;
    private int damageLifesteal;
    private int discardDamage;
    private static final byte BLAAAAAM = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte LIFESTEAL = 2;
    private static final byte DEBUFF = 3;
    private static final byte DISCARDION = 4;
    private static final byte REV_POT = 5;
    private boolean firstTurn = true;

    public LichLord(float x, float y) {
        super(NAME, "LichLord", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/LichLord.png", x, y); // Initializes the monster.

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) { // Checks if your Ascension is 7 or above...
            this.setHp(A_9_HP_MIN, A_9_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 19) {// 82
            this.damageBlaaam = ASC_4_DAMAGE_BLAAAM;// 83
            this.damageLifesteal = ASC_4_DAMAGE_LIFESTEAL;
            this.discardDamage = ASC_4_DISCARD_DAMAGE;
        } else if (AbstractDungeon.ascensionLevel >= 4) {// 86
            this.damageBlaaam = ASC_4_DAMAGE_BLAAAM;// 83
            this.damageLifesteal = ASC_4_DAMAGE_LIFESTEAL;
            this.discardDamage = ASC_4_DISCARD_DAMAGE;
        } else {
            this.damageBlaaam = DAMAGE_BLAAAM;// 83
            this.damageLifesteal = DAMAGE_LIFESTEAL;
            this.discardDamage = DISCARD_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, this.damageBlaaam));
        this.damage.add(new DamageInfo(this, this.damageLifesteal));
        this.damage.add(new DamageInfo(this, this.discardDamage));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();// 109
        AbstractDungeon.scene.fadeOutAmbiance();// 110
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");// 111
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LichPhylacPower(this, this, 1), 1));
        if (AbstractDungeon.ascensionLevel>=19)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 3), 3));
        }
    }

    public void takeTurn() {
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VampirePhylacAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.POISON));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));// 154
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(2), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PoisonPower(AbstractDungeon.player, this, 2), 2));
                break;
            case 5:
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new LichPhylac(150.0F, 0.0F), true));// 61
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        boolean isThereLivingPhylac = false;
        Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
        while (var3.hasNext()) {
            AbstractMonster m = (AbstractMonster) var3.next();
            if (m instanceof LichPhylac && !m.isDead && !m.isDying) {
                isThereLivingPhylac = true;
            }
        }
        if (!isThereLivingPhylac) {
            this.setMove((byte) 5, Intent.UNKNOWN);
        } else {
            ArrayList<Integer> wahoo = new ArrayList<>();
            wahoo.add(0);
            wahoo.add(1);
            wahoo.add(2);
            wahoo.add(3);
            if (this.lastMove((byte)1))
            {
                wahoo.remove(0);
            }
            if (this.lastMove((byte)2))
            {
                wahoo.remove(1);
            }
            if (this.lastMove((byte)3))
            {
                wahoo.remove(2);
            }
            if (this.lastMove((byte)4))
            {
                wahoo.remove(3);
            }
            int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size()-1));
            if (waaa == 0) {
                this.setMove((byte) 1, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(0)).base);
            } else if (waaa == 1) {
                this.setMove((byte) 2, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(1)).base);
            } else if (waaa == 2) {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            } else if (waaa == 3) {
                this.setMove((byte) 4, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(2)).base);
            }
        }
    }

    public void die() { // When this monster dies...
        this.useFastShakeAnimation(5.0F);// 320
        CardCrawlGame.screenShake.rumble(4.0F);// 321
        super.die();// 322
        this.onBossVictoryLogic();// 323
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

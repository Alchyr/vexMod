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
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.unique.SpawnDaggerAction;
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
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import vexMod.VexMod;
import vexMod.powers.*;

import java.util.ArrayList;
import java.util.Iterator;

public class DaggerThrower extends AbstractMonster {
    public static final String ID = VexMod.makeID("DaggerThrower"); // Makes the monster ID based on your mod's ID. For example: theDefault:BaseMonster
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grabs strings from your language pack based on ID>
    public static final String NAME = monsterstrings.NAME; // Pulls name,
    public static final String[] DIALOG = monsterstrings.DIALOG; // and dialog text from strings.
    private static final int HP_MIN = 230; // Always good to back up your health and move values.
    private static final int HP_MAX = 230;
    private static final int A_9_HP_MIN = 244; // HP moves up at Ascension 7.
    private static final int A_9_HP_MAX = 244;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int DAMAGE_NO_DAGGER = 5;
    private static final int ASC_4_DAMAGE_NO_DAGGER = 6;
    private static final int DAMAGE_DAG = 4;
    private static final int ASC_4_DAMAGE_DAG = 5;
    private int damageDagger;
    private int damageNoDagger;
    private static final byte DAMAGE_DAGGER = 1; // Not sure what this is for myself. Guess it's just attack names.
    private static final byte BUFF_DAGGER = 2;
    private static final byte WEAK_DAGGER = 3;
    private static final byte DAMAGE_NODAG = 4;
    private static final byte BUFF_NODAG = 5;
    private static final byte WEAK_NODAG = 6;
    private boolean firstTurn = true;
    private boolean dagToggle;
    private boolean dagToggleToggle;

    public DaggerThrower(float x, float y) {
        super(NAME, "DaggerThrower", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/DaggerPharaoh.png", x, y); // Initializes the monster.

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) { // Checks if your Ascension is 7 or above...
            this.setHp(A_9_HP_MIN, A_9_HP_MAX); // and increases HP if so.
        } else {
            this.setHp(HP_MIN, HP_MAX); // Provides regular HP values here otherwise.
        }

        if (AbstractDungeon.ascensionLevel >= 19) {// 82
            this.damageDagger = ASC_4_DAMAGE_DAG;// 83
            this.damageNoDagger = ASC_4_DAMAGE_NO_DAGGER;
        } else if (AbstractDungeon.ascensionLevel >= 4) {// 86
            this.damageDagger = ASC_4_DAMAGE_DAG;// 83
            this.damageNoDagger = ASC_4_DAMAGE_NO_DAGGER;
        } else {
            this.damageDagger = DAMAGE_DAG;// 83
            this.damageNoDagger = DAMAGE_NO_DAGGER;
        }

        dagToggle = true;
        dagToggleToggle = false;
        this.damage.add(new DamageInfo(this, this.damageDagger));
        this.damage.add(new DamageInfo(this, this.damageNoDagger));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {// 110
            CardCrawlGame.music.unsilenceBGM();// 111
            AbstractDungeon.scene.fadeOutAmbiance();// 112
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");// 113
        }
    }// 120

    public void takeTurn() {
        int numOfDaggers = 0;
        if (this.firstTurn) { // If this is the first turn,
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F)); // Speak the stuff in DIALOG[0],
            this.firstTurn = false; // Then ensure it's no longer the first turn.
        }
        AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));// 43
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.NONE));// 93 94
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));// 43
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.NONE));// 93 94
                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var3.hasNext()) {
                    AbstractMonster m = (AbstractMonster) var3.next();
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {// 60
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));// 61
                } else if (numOfDaggers == 1) {// 62
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));// 63
                } else if (numOfDaggers == 2) {// 64
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));// 65
                }
                break;
            case 2:
                Iterator var4 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var4.hasNext()) {
                    AbstractMonster m = (AbstractMonster) var4.next();
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {// 60
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));// 61
                } else if (numOfDaggers == 1) {// 62
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));// 63
                } else if (numOfDaggers == 2) {// 64
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));// 65
                }
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();// 116

                while (true) {
                    if (!var1.hasNext()) {
                        break;
                    }

                    AbstractMonster m = (AbstractMonster) var1.next();
                    if (!m.isDying) {// 121
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 1), 1));// 122
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                Iterator var5 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var5.hasNext()) {
                    AbstractMonster m = (AbstractMonster) var5.next();
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {// 60
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));// 61
                } else if (numOfDaggers == 1) {// 62
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));// 63
                } else if (numOfDaggers == 2) {// 64
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));// 65
                }
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));// 43
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.NONE));// 93 94
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));// 43
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AttackEffect.NONE));
                break;
            case 5:
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();// 116

                while (true) {
                    if (!var2.hasNext()) {
                        break;
                    }

                    AbstractMonster m = (AbstractMonster) var2.next();
                    if (!m.isDying) {// 121
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2), 2));// 122
                    }
                }

                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 10));// 108
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) { // Gets a number for movement.
        if (dagToggle) {
            ArrayList<Integer> wahoo = new ArrayList<>();
            wahoo.add(0);
            wahoo.add(1);
            wahoo.add(2);
            if (this.lastMove((byte) 1) || this.lastMove((byte) 4)) {
                wahoo.remove(0);
            }
            if (this.lastMove((byte) 2) || this.lastMove((byte) 5)) {
                wahoo.remove(1);
            }
            if (this.lastMove((byte) 3) || this.lastMove((byte) 6)) {
                wahoo.remove(2);
            }
            int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size() - 1));
            if (waaa == 0) {
                this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
            } else if (waaa == 1) {
                this.setMove((byte) 2, Intent.BUFF);
            } else if (waaa == 2) {
                this.setMove((byte) 3, Intent.DEBUFF);
            }
            dagToggle = false;
        } else {
            ArrayList<Integer> wahoo = new ArrayList<>();
            wahoo.add(0);
            wahoo.add(1);
            wahoo.add(2);
            if (this.lastMove((byte) 1) || this.lastMove((byte) 4)) {
                wahoo.remove(0);
            }
            if (this.lastMove((byte) 2) || this.lastMove((byte) 5)) {
                wahoo.remove(1);
            }
            if (this.lastMove((byte) 3) || this.lastMove((byte) 6)) {
                wahoo.remove(2);
            }
            int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size() - 1));
            if (waaa == 0) {
                this.setMove((byte) 4, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 2, true);
            } else if (waaa == 1) {
                this.setMove((byte) 5, Intent.BUFF);
            } else if (waaa == 2) {
                this.setMove((byte) 6, Intent.DEBUFF);
            }
            if (dagToggleToggle || AbstractDungeon.ascensionLevel>=19) {
                dagToggle = true;
                dagToggleToggle = false;
            } else {
                dagToggleToggle = true;
            }
        }
    }

    public void die() { // When this monster dies...
        this.useFastShakeAnimation(5.0F);// 260
        CardCrawlGame.screenShake.rumble(4.0F);// 261
        ++this.deathTimer;// 262
        super.die();// 263
        this.onBossVictoryLogic();// 264
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 266

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying) {// 267
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));// 268
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));// 269
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));// 270
            }
        }
        // CardCrawlGame.sound.play("SOTE_SFX_POTION_1_v2"); // And it croaks too.
    }

}// You made it! End of monster.

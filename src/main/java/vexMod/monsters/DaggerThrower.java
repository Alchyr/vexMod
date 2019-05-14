package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import vexMod.VexMod;

import java.util.ArrayList;

public class DaggerThrower extends AbstractMonster {
    public static final String ID = VexMod.makeID("DaggerThrower");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 230;
    private static final int HP_MAX = 230;
    private static final int A_9_HP_MIN = 244;
    private static final int A_9_HP_MAX = 244;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int DAMAGE_NO_DAGGER = 5;
    private static final int ASC_4_DAMAGE_NO_DAGGER = 6;
    private static final int DAMAGE_DAG = 4;
    private static final int ASC_4_DAMAGE_DAG = 5;
    private boolean firstTurn = true;
    private boolean dagToggle;
    private boolean dagToggleToggle;

    public DaggerThrower(float x, float y) {
        super(NAME, "DaggerThrower", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/DaggerPharaoh.png", x, y);

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int damageDagger;
        int damageNoDagger;
        if (AbstractDungeon.ascensionLevel >= 19) {
            damageDagger = ASC_4_DAMAGE_DAG;
            damageNoDagger = ASC_4_DAMAGE_NO_DAGGER;
        } else if (AbstractDungeon.ascensionLevel >= 4) {
            damageDagger = ASC_4_DAMAGE_DAG;
            damageNoDagger = ASC_4_DAMAGE_NO_DAGGER;
        } else {
            damageDagger = DAMAGE_DAG;
            damageNoDagger = DAMAGE_NO_DAGGER;
        }

        dagToggle = true;
        dagToggleToggle = false;
        this.damage.add(new DamageInfo(this, damageDagger));
        this.damage.add(new DamageInfo(this, damageNoDagger));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }
    }

    public void takeTurn() {
        int numOfDaggers = 0;
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }
        AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.NONE));
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));
                } else if (numOfDaggers == 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));
                } else if (numOfDaggers == 2) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));
                }
                break;
            case 2:
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));
                } else if (numOfDaggers == 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));
                } else if (numOfDaggers == 2) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));
                }

                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {

                    if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 1), 1));
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof SnakeDagger && !m.isDead && !m.isDying) {
                        numOfDaggers++;
                    }
                }
                if (numOfDaggers < 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-220.0F, 90.0F), true));
                } else if (numOfDaggers == 1) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(180.0F, 320.0F), true));
                } else if (numOfDaggers == 2) {
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new SnakeDagger(-250.0F, 310.0F), true));
                }
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.NONE));
                break;
            case 5:

                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {

                    if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2), 2));
                    }
                }

                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 10));
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
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
                this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
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
                this.setMove((byte) 4, Intent.ATTACK, this.damage.get(1).base, 2, true);
            } else if (waaa == 1) {
                this.setMove((byte) 5, Intent.BUFF);
            } else if (waaa == 2) {
                this.setMove((byte) 6, Intent.DEBUFF);
            }
            if (dagToggleToggle || AbstractDungeon.ascensionLevel >= 19) {
                dagToggle = true;
                dagToggleToggle = false;
            } else {
                dagToggleToggle = true;
            }
        }
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        ++this.deathTimer;
        super.die();
        this.onBossVictoryLogic();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }

    }

}

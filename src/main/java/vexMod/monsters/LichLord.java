package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import vexMod.VexMod;
import vexMod.actions.VampirePhylacAction;
import vexMod.powers.LichPhylacPower;

import java.util.ArrayList;

public class LichLord extends AbstractMonster {
    public static final String ID = VexMod.makeID("LichLord");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 135;
    private static final int HP_MAX = 135;
    private static final int A_9_HP_MIN = 150;
    private static final int A_9_HP_MAX = 150;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 379.0F;
    private static final float HB_H = 434.0F;
    private static final int DAMAGE_BLAAAM = 21;
    private static final int ASC_4_DAMAGE_BLAAAM = 25;
    private static final int DAMAGE_LIFESTEAL = 18;
    private static final int ASC_4_DAMAGE_LIFESTEAL = 22;
    private static final int DISCARD_DAMAGE = 15;
    private static final int ASC_4_DISCARD_DAMAGE = 18;
    private boolean firstTurn = true;

    public LichLord(float x, float y) {
        super(NAME, "LichLord", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/LichLord.png", x, y);

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int damageBlaaam;
        int damageLifesteal;
        int discardDamage;
        if (AbstractDungeon.ascensionLevel >= 19) {
            damageBlaaam = ASC_4_DAMAGE_BLAAAM;
            damageLifesteal = ASC_4_DAMAGE_LIFESTEAL;
            discardDamage = ASC_4_DISCARD_DAMAGE;
        } else if (AbstractDungeon.ascensionLevel >= 4) {
            damageBlaaam = ASC_4_DAMAGE_BLAAAM;
            damageLifesteal = ASC_4_DAMAGE_LIFESTEAL;
            discardDamage = ASC_4_DISCARD_DAMAGE;
        } else {
            damageBlaaam = DAMAGE_BLAAAM;
            damageLifesteal = DAMAGE_LIFESTEAL;
            discardDamage = DISCARD_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, damageBlaaam));
        this.damage.add(new DamageInfo(this, damageLifesteal));
        this.damage.add(new DamageInfo(this, discardDamage));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new LichPhylacPower(this, this)));
        if (AbstractDungeon.ascensionLevel >= 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 3), 3));
        }
    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VampirePhylacAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.POISON));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PoisonPower(AbstractDungeon.player, this, 2), 2));
                break;
            case 5:
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new LichPhylac(150.0F, 0.0F), true));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        boolean isThereLivingPhylac = false;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
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
            if (this.lastMove((byte) 1)) {
                wahoo.remove(0);
            }
            if (this.lastMove((byte) 2)) {
                wahoo.remove(1);
            }
            if (this.lastMove((byte) 3)) {
                wahoo.remove(2);
            }
            if (this.lastMove((byte) 4)) {
                wahoo.remove(3);
            }
            int waaa = wahoo.get(AbstractDungeon.monsterRng.random(wahoo.size() - 1));
            if (waaa == 0) {
                this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
            } else if (waaa == 1) {
                this.setMove((byte) 2, Intent.ATTACK_BUFF, this.damage.get(1).base);
            } else if (waaa == 2) {
                this.setMove((byte) 3, Intent.STRONG_DEBUFF);
            } else if (waaa == 3) {
                this.setMove((byte) 4, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
            }
        }
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        this.onBossVictoryLogic();

    }

}

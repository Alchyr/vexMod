package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import vexMod.VexMod;

public class BombBelcher extends AbstractMonster {
    public static final String ID = VexMod.makeID("BombBelcher");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 175;
    private static final int HP_MAX = 180;
    private static final int A_8_HP_MIN = 185;
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

    public BombBelcher(float x, float y) {
        super(NAME, "BombBelcher", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/BombBelcher.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackBlockDamage;
        int bigAttackDamage;
        int frailAttackDamage;
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackBlockBlock = A_3_ATTACK_BLOCK_BLOCK;
            attackBlockDamage = A_3_ATTACK_BLOCK_DAMAGE;
            bigAttackDamage = A_3_BIG_ATTACK_DAMAGE;
            frailAttackDamage = A_3_FRAIL_ATTACK_DAMAGE;
        } else {
            this.attackBlockBlock = ATTACK_BLOCK_BLOCK;
            attackBlockDamage = ATTACK_BLOCK_DAMAGE;
            bigAttackDamage = BIG_ATTACK_DAMAGE;
            frailAttackDamage = FRAIL_ATTACK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackBlockDamage));
        this.damage.add(new DamageInfo(this, bigAttackDamage));
        this.damage.add(new DamageInfo(this, frailAttackDamage));

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 10), 10));
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 6), 6));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 3), 3));
        }
    }

    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                this.setMove((byte) 2, Intent.ATTACK_BUFF, this.damage.get(1).base);
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                this.setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                this.setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(0).base);
        }
    }

    protected void getMove(int num) {
        this.setMove((byte) 2, Intent.ATTACK_BUFF, this.damage.get(1).base);
    }

    public void die() {
        super.die();

    }

}

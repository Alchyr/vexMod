package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import vexMod.VexMod;

import java.util.ArrayList;

public class Combatant extends AbstractMonster {
    public static final String ID = VexMod.makeID("Combatant");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 100;
    private static final int HP_MAX = 108;
    private static final int A_8_HP_MIN = 113;
    private static final int A_8_HP_MAX = 117;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int ATTACK_BLOCK_DAMAGE = 12;
    private static final int ATTACK_BLOCK_BLOCK = 7;
    private static final int BIG_ATTACK_DAMAGE = 16;
    private static final int SELF_HEAL_ATTACK_DAMAGE = 10;
    private static final int A_3_ATTACK_BLOCK_DAMAGE = 14;
    private static final int A_3_ATTACK_BLOCK_BLOCK = 9;
    private static final int A_3_BIG_ATTACK_DAMAGE = 19;
    private static final int A_3_SELF_HEAL_ATTACK_DAMAGE = 13;
    private int attackBlockBlock;
    private boolean firstTurn = true;

    public Combatant(float x, float y) {
        super(NAME, "Combatant", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/Combatant.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackBlockDamage;
        int bigAttackDamage;
        int selfHealAttackDamage;
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackBlockBlock = A_3_ATTACK_BLOCK_BLOCK;
            attackBlockDamage = A_3_ATTACK_BLOCK_DAMAGE;
            bigAttackDamage = A_3_BIG_ATTACK_DAMAGE;
            selfHealAttackDamage = A_3_SELF_HEAL_ATTACK_DAMAGE;
        } else {
            this.attackBlockBlock = ATTACK_BLOCK_BLOCK;
            attackBlockDamage = ATTACK_BLOCK_DAMAGE;
            bigAttackDamage = BIG_ATTACK_DAMAGE;
            selfHealAttackDamage = SELF_HEAL_ATTACK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackBlockDamage));
        this.damage.add(new DamageInfo(this, bigAttackDamage));
        this.damage.add(new DamageInfo(this, selfHealAttackDamage));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 3), 3));
        }
    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.attackBlockBlock));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.SLASH_DIAGONAL));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 4));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
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

    public void die() {
        super.die();

    }

}

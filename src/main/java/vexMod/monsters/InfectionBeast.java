package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import vexMod.VexMod;
import vexMod.cards.Virus;

public class InfectionBeast extends AbstractMonster {
    public static final String ID = VexMod.makeID("InfectionBeast");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 200;
    private static final int HP_MAX = 205;
    private static final int A_8_HP_MIN = 212;
    private static final int A_8_HP_MAX = 216;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 441.0F;
    private static final float HB_H = 450.0F;
    private static final int ATTACK_LIFESTEAL_DAMAGE = 16;
    private static final int A_3_ATTACK_LIFESTEAL_DAMAGE = 20;
    private boolean firstTurn = true;

    public InfectionBeast(float x, float y) {
        super(NAME, "InfectionBeast", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/InfectionBeast.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackLifestealDamage;
        if (AbstractDungeon.ascensionLevel >= 3) {
            attackLifestealDamage = A_3_ATTACK_LIFESTEAL_DAMAGE;
        } else {
            attackLifestealDamage = ATTACK_LIFESTEAL_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackLifestealDamage));

    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 6), 6));
        }
    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.POISON));
                break;
            case 2:
                if (!AbstractDungeon.player.hasPower(ConstrictedPower.POWER_ID)) {
                    int letsSee = AbstractDungeon.monsterRng.random(4);
                    if (letsSee == 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                    } else if (letsSee == 1) {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), 2, true, true));
                    } else if (letsSee == 2) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 3));
                    } else if (letsSee == 3) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 5), 5));
                    } else if (letsSee == 4) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                    }
                } else {
                    int letsSee = AbstractDungeon.monsterRng.random(3);
                    if (letsSee == 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                    } else if (letsSee == 1) {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), 2, true, true));
                    } else if (letsSee == 2) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 3));
                    } else if (letsSee == 3) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(), 1, true, true));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.firstTurn) {
            this.setMove((byte) 3, Intent.STRONG_DEBUFF);
        } else {
            if (this.lastMove((byte) 2)) {
                this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
            } else {
                this.setMove((byte) 2, Intent.DEBUFF);
            }
        }
    }


    public void die() {
        super.die();

    }

}

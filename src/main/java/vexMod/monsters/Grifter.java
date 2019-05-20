package vexMod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import vexMod.VexMod;
import vexMod.powers.GriftingPower;
import vexMod.vfx.RelicYoinkEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grifter extends AbstractMonster {
    public static final String ID = VexMod.makeID("Grifter");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 152;
    private static final int HP_MAX = 152;
    private static final int A_7_HP_MIN = 160;
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
    private int attackBlockBlock;
    private int defendBlock;
    private boolean firstTurn;
    private int moveAmt;

    public Grifter(float x, float y) {
        super(NAME, "Grifter", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/Grifter.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        int attackBlockDmg;
        int attackWeakDmg;
        int recklessDmg;
        if (AbstractDungeon.ascensionLevel >= 17) {
            attackWeakDmg = ATTACKWEAK_DAMAGE;
            attackBlockDmg = ATTACKBLOCK_DAMAGE;
            recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            attackWeakDmg = ATTACKWEAK_DAMAGE;
            attackBlockDmg = ATTACKBLOCK_DAMAGE;
            recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        } else {
            attackWeakDmg = ATTACKWEAK_DAMAGE;
            attackBlockDmg = ATTACKBLOCK_DAMAGE;
            recklessDmg = RECKLESS_DAMAGE;
            this.attackBlockBlock = ATTACKBLOCK_BLOCK;
            this.defendBlock = DEFEND_BLOCK;
        }

        this.damage.add(new DamageInfo(this, attackWeakDmg));
        this.damage.add(new DamageInfo(this, attackBlockDmg));
        this.damage.add(new DamageInfo(this, recklessDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GriftingPower(this, this, 1)));
    }

    public void takeTurn() {
        if (this.firstTurn) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            this.firstTurn = false;
        }

        switch (this.nextMove) {
            case 1:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.effectList.add(new RelicYoinkEffect(crelic, this));
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 2:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.effectList.add(new RelicYoinkEffect(crelic, this));
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, attackBlockBlock));
                break;
            case 3:
                if (AbstractDungeon.player.relics.size() > 0) {
                    ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
                    Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
                    AbstractRelic crelic = relics.get(0);
                    crelic.flash();
                    AbstractDungeon.player.loseRelic(crelic.relicId);
                    AbstractDungeon.effectList.add(new RelicYoinkEffect(crelic, this));
                    AbstractDungeon.getCurrRoom().addRelicToRewards(crelic);
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, defendBlock));
                break;
            case 5:
                AbstractDungeon.getCurrRoom().rewards.removeIf(rewardItem -> rewardItem.type == RewardItem.RewardType.RELIC);
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.3F, 2.5F));
                AbstractDungeon.getCurrRoom().mugged = true;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(this.hb.cX, this.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 5, Intent.ESCAPE));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        moveAmt++;
        if (moveAmt == 1) {
            this.setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        } else if (moveAmt == 2) {
            this.setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
        } else if (moveAmt == 3) {
            this.setMove((byte) 3, Intent.ATTACK_DEFEND, this.damage.get(2).base);
        } else if (moveAmt == 4) {
            this.setMove((byte) 4, Intent.DEFEND);
        } else if (moveAmt == 5) {
            this.setMove((byte) 5, Intent.ESCAPE);
        }
    }

    public void die() {
        super.die();

    }

}

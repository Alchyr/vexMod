package vexMod.monsters;

import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;

public class LichPhylac extends AbstractMonster {
    public static final String ID = VexMod.makeID("LichPhylac");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 100;
    private static final int HP_MAX = 100;
    private static final int A_9_HP_MIN = 110;
    private static final int A_9_HP_MAX = 110;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 239.0F;
    private static final float HB_H = 295.0F;

    public LichPhylac(float x, float y) {
        super(NAME, "LichPhylac", 25, HB_X, HB_Y, HB_W, HB_H, "vexModResources/images/monsters/LichPhylac.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

    }

    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.NONE);
    }

    public void die() {
        super.die();

    }

}

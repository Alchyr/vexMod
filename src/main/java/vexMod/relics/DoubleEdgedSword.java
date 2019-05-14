package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class DoubleEdgedSword extends CustomRelic {


    public static final String ID = VexMod.makeID("DoubleEdgedSword");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DoubleEdgedSword.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DoubleEdgedSword.png"));

    public DoubleEdgedSword() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            m.addPower(new ThornsPower(m, 1));
        }

        AbstractDungeon.onModifyPower();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
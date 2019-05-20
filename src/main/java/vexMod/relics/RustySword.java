package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RustySword extends CustomRelic {


    public static final String ID = VexMod.makeID("RustySword");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RustySword.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RustySword.png"));

    public RustySword() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -1), -1));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            m.addPower(new StrengthPower(m, -2));
        }

        AbstractDungeon.onModifyPower();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
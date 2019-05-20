package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.powers.PlayerTimeWarp;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TimeEaterHat extends CustomRelic {


    public static final String ID = VexMod.makeID("TimeEaterHat");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TimeEaterHat.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TimeEaterHat.png"));


    public TimeEaterHat() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlayerTimeWarp(AbstractDungeon.player, 12, 2), 1));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

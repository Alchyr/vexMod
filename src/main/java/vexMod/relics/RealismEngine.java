package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RealismEngine extends CustomRelic {


    public static final String ID = VexMod.makeID("RealismEngine");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RealismEngine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public RealismEngine() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onUsePotion() {
        this.flash();// 24
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {// 25
            AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, DESCRIPTIONS[1], true));
        }
    }// 32

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

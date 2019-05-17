package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.actions.TargetAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class HealthChanger extends CustomRelic implements ClickableRelic {


    public static final String ID = VexMod.makeID("HealthChanger");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HealthChanger.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public HealthChanger() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    private boolean usedThisCombat = false;

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.actionManager.turnHasEnded && !this.usedThisCombat) {
            new TargetAction(this);
            this.usedThisCombat = true;
        }
    }


    @Override
    public void onVictory() {
        this.usedThisCombat = false;
    }
}
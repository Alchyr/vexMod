package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnMelodyPlayedHook;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class AudienceDoll extends CustomRelic implements OnMelodyPlayedHook {

    public static final String ID = VexMod.makeID("AudienceDoll");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AudienceDoll.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public AudienceDoll() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onMelodyPlayed(AbstractMelody melody) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

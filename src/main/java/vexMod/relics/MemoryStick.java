package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MemoryStick extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("MemoryStick");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MemoryLocket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MemoryLocket.png"));


    public MemoryStick() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EquilibriumPower(AbstractDungeon.player, 1), 1));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

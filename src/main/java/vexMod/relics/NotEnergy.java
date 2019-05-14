package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class NotEnergy extends CustomRelic {


    public static final String ID = VexMod.makeID("NotEnergy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NotEnergy.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NotEnergy.png"));


    public NotEnergy() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, getEnergyTaunt(), 0.0F, 3.5F));
    }

    private String getEnergyTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[1]);
        taunts.add(DESCRIPTIONS[2]);
        taunts.add(DESCRIPTIONS[3]);
        taunts.add(DESCRIPTIONS[4]);
        taunts.add(DESCRIPTIONS[5]);
        taunts.add(DESCRIPTIONS[6]);
        taunts.add(DESCRIPTIONS[7]);
        taunts.add(DESCRIPTIONS[8]);
        taunts.add(DESCRIPTIONS[9]);
        taunts.add(DESCRIPTIONS[10]);
        taunts.add(DESCRIPTIONS[11]);
        taunts.add(DESCRIPTIONS[12]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    @Override
    public int getPrice() {
        return 1;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

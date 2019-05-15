package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Pepega extends CustomRelic implements BetterOnLoseHpRelic {


    public static final String ID = VexMod.makeID("Pepega");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Pepega.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Pepega.png"));


    public Pepega() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getPepegaTaunt(), 0.0F, 3.0F));
        return damageAmount;
    }

    private String getPepegaTaunt() {
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
        taunts.add(DESCRIPTIONS[13]);
        taunts.add(DESCRIPTIONS[14]);
        taunts.add(DESCRIPTIONS[15]);
        taunts.add(DESCRIPTIONS[16]);
        taunts.add(DESCRIPTIONS[17]);
        taunts.add(DESCRIPTIONS[18]);
        taunts.add(DESCRIPTIONS[19]);
        taunts.add(DESCRIPTIONS[20]);
        taunts.add(DESCRIPTIONS[21]);
        taunts.add(DESCRIPTIONS[22]);
        taunts.add(DESCRIPTIONS[23]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Relics.SkyMirror;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FutureSight extends CustomRelic {

    public static final String ID = VexMod.makeID("FutureSight");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FutureSight.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FutureSight.png"));

    public FutureSight() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SkyMirror.ID)) {// 52
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {// 53
                if (AbstractDungeon.player.relics.get(i).relicId.equals(SkyMirror.ID)) {// 54
                    this.instantObtain(AbstractDungeon.player, i, true);// 55
                    break;// 56
                }
            }
        } else {
            super.obtain();// 60
        }

    }// 62

    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.player.hasRelic(SkyMirror.ID));
    }

    @Override
    public String getUpdatedDescription() {
        String name = (new SkyMirror()).name;// 38
        StringBuilder sb = new StringBuilder();// 39
        String[] var3 = name.split(" ");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {// 40
            String word = var3[var5];
            sb.append("[#").append(AstrologerMod.STARS.toString()).append("]").append(word).append("[] ");// 41
        }

        sb.setLength(sb.length() - 1);// 43
        sb.append("[#").append(AstrologerMod.STARS.toString()).append("]");// 44
        return this.DESCRIPTIONS[0] + sb.toString() + this.DESCRIPTIONS[1];// 46
    }

}

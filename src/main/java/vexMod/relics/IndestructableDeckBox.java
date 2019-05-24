package vexMod.relics;

import ThePokerPlayer.PokerPlayerMod;
import ThePokerPlayer.relics.DeckCase;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class IndestructableDeckBox extends CustomRelic {

    public static final String ID = VexMod.makeID("IndestructableDeckBox");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IndestructableDeckBox.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IndestructableDeckBox.png"));

    public IndestructableDeckBox() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(DeckCase.ID)) {// 52
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {// 53
                if (AbstractDungeon.player.relics.get(i).relicId.equals(DeckCase.ID)) {// 54
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
        return (AbstractDungeon.player.hasRelic(DeckCase.ID));
    }

    @Override
    public String getUpdatedDescription() {
        String name = (new DeckCase()).name;// 38
        StringBuilder sb = new StringBuilder();// 39
        String[] var3 = name.split(" ");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {// 40
            String word = var3[var5];
            sb.append("[#").append(PokerPlayerMod.POKER_PLAYER_GRAY.toString()).append("]").append(word).append("[] ");// 41
        }

        sb.setLength(sb.length() - 1);// 43
        sb.append("[#").append(PokerPlayerMod.POKER_PLAYER_GRAY.toString()).append("]");// 44
        return this.DESCRIPTIONS[0] + sb.toString() + this.DESCRIPTIONS[1];// 46
    }

}

package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ChompingNoodles extends CustomRelic {


    public static final String ID = VexMod.makeID("ChompingNoodles");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChompingNoodles.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChompingNoodles.png"));


    public ChompingNoodles() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof EventRoom) {
            this.flash();
            AbstractDungeon.player.increaseMaxHp(3, true);
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.HP_LOSS));
        }

    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 40);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

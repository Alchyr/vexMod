package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GoldBooster extends CustomRelic {


    public static final String ID = VexMod.makeID("GoldBooster");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldBooster.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldBooster.png"));


    public GoldBooster() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        this.flash();
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(7);
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 40) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

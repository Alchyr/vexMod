package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ImaginarySword extends CustomRelic {


    public static final String ID = VexMod.makeID("ImaginarySword");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ImaginarySword.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ImaginarySword.png"));

    private boolean cardSelected = true;


    public ImaginarySword() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getAttacks(), 2, this.DESCRIPTIONS[1], false, false, false, true);
    }

    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            this.cardSelected = true;
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(1), (float) Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }
}

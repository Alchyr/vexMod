package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ImprovementManual extends CustomRelic {


    public static final String ID = VexMod.makeID("ImprovementManual");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ImprovementManual.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ImprovementManual.png"));

    private boolean cardSelected = true;
    private int upgradeAbleCards;

    public ImprovementManual() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        upgradeAbleCards = AbstractDungeon.player.masterDeck.getUpgradableCards().size();
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        if (upgradeAbleCards > 4) {
            upgradeAbleCards = 4;
        }
        if (upgradeAbleCards > 0) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), upgradeAbleCards, this.DESCRIPTIONS[1], false, false, false, false);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == upgradeAbleCards) {
            this.cardSelected = true;

            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                card.upgrade();
                float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                card.stopGlowing();
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

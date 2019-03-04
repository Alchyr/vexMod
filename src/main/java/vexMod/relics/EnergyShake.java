package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class EnergyShake extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("EnergyShake");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public EnergyShake() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        int remove;
        for(remove = 0; remove < 1; ++remove) {
            AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());
        }

        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
        remove = -1;

        for(int i = 0; i < AbstractDungeon.combatRewardScreen.rewards.size(); ++i) {
            if (((RewardItem)AbstractDungeon.combatRewardScreen.rewards.get(i)).type == RewardItem.RewardType.CARD) {
                remove = i;
                break;
            }
        }

        if (remove != -1) {
            AbstractDungeon.combatRewardScreen.rewards.remove(remove);
        }

    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

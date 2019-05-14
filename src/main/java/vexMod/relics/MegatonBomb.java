package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.SuperRareRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MegatonBomb extends CustomRelic implements ClickableRelic, SuperRareRelic {

    public static final String ID = VexMod.makeID("MegatonBomb");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MegatonBomb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static boolean loseRelic = false;

    public MegatonBomb() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public static void relicBullshit() {
        if (loseRelic) {
            AbstractDungeon.player.loseRelic(MegatonBomb.ID);
            loseRelic = false;
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public void onRightClick() {
        if (!isObtained) {
            return;
        }

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                m.isDead = true;
                m.isDying = true;
            }
        }

        CardCrawlGame.sound.play("UI_CLICK_1");
        this.flash();
        loseRelic = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
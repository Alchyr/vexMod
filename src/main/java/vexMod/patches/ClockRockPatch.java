package vexMod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import vexMod.relics.FluxCapacitor;

import static com.megacrit.cardcrawl.ui.panels.TopPanel.LABEL;
import static com.megacrit.cardcrawl.ui.panels.TopPanel.MSG;

@SpirePatch(
        clz = TopPanel.class,
        method = "render",
        paramtypez = {
                SpriteBatch.class
        }
)
public class ClockRockPatch {
    public static void Postfix(TopPanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.player.hasRelic(FluxCapacitor.ID)) {// 1066
            __instance.timerHb.update();// 1067
            sb.draw(ImageMaster.TIMER_ICON, (float) Settings.WIDTH - 617.0F * Settings.scale, (float) ReflectionHacks.getPrivateStatic(TopPanel.class, "ICON_Y"), (float) ReflectionHacks.getPrivateStatic(TopPanel.class, "ICON_W"), (float) ReflectionHacks.getPrivateStatic(TopPanel.class, "ICON_W"));// 1068
            if (CardCrawlGame.stopClock) {// 1069
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, CharStat.formatHMSM(CardCrawlGame.playtime), (float) Settings.WIDTH - 557.0F * Settings.scale, (float) ReflectionHacks.getPrivate(__instance, TopPanel.class, "titleY"), Settings.GREEN_TEXT_COLOR);// 1070 1073
            } else {
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, CharStat.formatHMSM(CardCrawlGame.playtime), (float) Settings.WIDTH - 557.0F * Settings.scale, (float) ReflectionHacks.getPrivate(__instance, TopPanel.class, "titleY"), Settings.RED_TEXT_COLOR);// 1078 1081
            }

            if (__instance.timerHb.hovered) {// 1086
                TipHelper.renderGenericTip((float) ReflectionHacks.getPrivateStatic(TopPanel.class, "TOP_RIGHT_TIP_X"), (float) ReflectionHacks.getPrivateStatic(TopPanel.class, "TIP_Y"), LABEL[5], MSG[7]);// 1087
            }

            __instance.timerHb.render(sb);// 1089
        }
    }

    public static void Prefix(TopPanel __instance, SpriteBatch sb) {
        LABEL[5] = "TICK TOCK";
        MSG[7] = "Time until your doom.";
    }
}
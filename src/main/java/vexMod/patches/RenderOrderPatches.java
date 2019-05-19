package vexMod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import vexMod.vfx.BouncingRelic;
import vexMod.vfx.JuggleRelic;

import java.util.ArrayList;

public class RenderOrderPatches {
    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "render"
    )
    public static class RenderOrderPatch {
        public static ArrayList<BouncingRelic> brs = new ArrayList<>();
        public static ArrayList<JuggleRelic> jrs = new ArrayList<>();

        public static void Postfix(CardCrawlGame __instance) {
            SpriteBatch sb = (SpriteBatch) ReflectionHacks.getPrivate(__instance, CardCrawlGame.class, "sb");
            sb.begin();
            sb.setColor(Color.WHITE);
            for (int i = 0; i < brs.size(); i++) {
                BouncingRelic br = brs.get(i);
                if (br == null) {
                    brs.remove(i--);
                } else {
                    br.renderCorrectly(sb);
                }
            }
            for (int i = 0; i < jrs.size(); i++) {
                JuggleRelic jr = jrs.get(i);
                if (jr == null) {
                    brs.remove(i--);
                } else {
                    jr.renderCorrectly(sb);
                }
            }
            sb.end();
        }
    }
}
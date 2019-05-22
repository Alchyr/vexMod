package vexMod.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class RelicRapidFireEffect extends AbstractGameEffect {

    public static final int flighttime = 20; //How long does 1 relic fly.
    public static final int delay = 5; //How long after the last relic started flying for the next one to start.

    public static final float gravity = 0.5F * Settings.scale;
    public static final float frictionX = 0.1F * Settings.scale;
    public static final float frictionY = 0.2F * Settings.scale;

    public static final int dispersalspeed = 1;
    public boolean finishedAction;
    private ArrayList<RelicInfo> relics = new ArrayList<>();
    private int damage;
    private int frames;
    private ArrayList<AbstractMonster> enemies = new ArrayList<>();
    private ArrayList<Integer> health = new ArrayList<>();


    public RelicRapidFireEffect(int damage) {
        this.damage = damage;


        for (final AbstractMonster am : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!am.isDead && !am.halfDead && !am.escaped) {
                enemies.add(am);
                health.add(am.currentHealth + am.currentBlock);
            }
        }

        ArrayList<AbstractRelic> tmp = new ArrayList<>();
        relics = new ArrayList<>();
        for (final AbstractRelic ar : AbstractDungeon.player.relics) {
            if (ar.relicId.startsWith("vexMod:")) {
                tmp.add(ar);
            }
        }

        while (tmp.size() > 0 && enemies.size() > 0) {
            int index = AbstractDungeon.cardRandomRng.random(enemies.size() - 1);
            AbstractCreature ac = enemies.get(index);
            if (health.get(index) - this.damage > 0) {
                health.add(index, health.get(index) - this.damage);
                index++;
            } else {
                enemies.remove(index);
            }
            health.remove(index);


            index = MathUtils.random(tmp.size() - 1);
            relics.add(new RelicInfo(tmp.get(index), ac));
            tmp.remove(index);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = 0; i <= frames / delay && i < relics.size(); i++) {
            relics.get(i).render(sb);
        }
        sb.setColor(Color.WHITE);
    }

    public void update() {
        finishedAction = true;
        boolean finishedEffect = true;

        for (int i = 0; i <= frames / delay && i < relics.size(); i++) {
            switch (relics.get(i).update()) {
                case 0:
                    finishedAction = false;
                case 1:
                case 2:
                    finishedEffect = false;
            }
        }
        frames++;

        if (finishedEffect) {
            this.isDone = true;
        }
    }

    public void dispose() {

    }

    class RelicInfo {
        FlashAtkImgEffect faie;
        private float x;
        private float y;
        private float targetX;
        private float targetY;
        private float rotation;
        private float radialvelocity;
        private float bounceplane;
        private float opacity;
        private int hit;
        private int frames;
        private AbstractCreature ac;
        private AbstractRelic ar;

        public RelicInfo(AbstractRelic ar, AbstractCreature ac) {
            targetX = ac.hb.cX + MathUtils.random(ac.hb.width) - ac.hb.width * 1 / 4;
            targetY = ac.hb.cY + MathUtils.random(ac.hb.height) - ac.hb.height * 1 / 4;

            x = ar.currentX;
            y = ar.currentY;

            this.ar = ar;
            this.ac = ac;

            hit = 0;
            frames = 0;

            bounceplane = ac.hb.y + MathUtils.random(ac.hb.height / 4, ac.hb.height / 4);

            opacity = 1F;

            rotation = 0;
            radialvelocity = 0;

            faie = new FlashAtkImgEffect(ac.hb.cX, ac.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, false);
        }

        public void render(SpriteBatch sb) {
            sb.setColor(1F, 1F, 1F, opacity);
            sb.draw(ar.img, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
            if (hit > 0 && !faie.isDone)
                faie.render(sb);
        }

        public int update() {
            if (hit == 0) {
                x = ar.currentX + (targetX - ar.currentX) / (float) flighttime * frames;
                y = ar.currentY + (targetY - ar.currentY) / (float) flighttime * frames;

                if (frames++ == flighttime) {
                    frames = 0;
                    hit = 1;

                    radialvelocity = MathUtils.random(-30, 30);

                    targetX = (targetX - ac.hb.cX - ac.hb.width / 4) / 4;
                    targetY = (targetY - ac.hb.cY) / 4;

                    AbstractDungeon.actionManager.addToBottom(new DamageAction(ac, new DamageInfo(ac, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true, true));
                }
            } else {
                this.targetX += (this.targetX > 0 ? -frictionX : frictionX);

                if (y + this.targetY <= bounceplane) {
                    this.targetY = Math.abs(this.targetY);
                    if (this.targetY > 1 * Settings.scale) {
                        this.radialvelocity = MathUtils.random(-30, 30);
                    } else {
                        this.radialvelocity = 0;
                    }
                    hit = 2;
                } else {
                    this.targetY -= (this.targetY > 0 ? frictionY : -frictionY);
                    this.targetY -= gravity;
                }
                x += targetX;
                y += targetY;
                rotation += radialvelocity;

                if (hit > 1) {
                    if (opacity <= 0F) {
                        opacity = 0F;
                        hit = 3;
                    } else {
                        opacity -= dispersalspeed / 300F;
                    }
                }
            }
            if (hit > 0 && !faie.isDone)
                faie.update();
            return hit;
        }
    }
}

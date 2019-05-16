package vexMod;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vexMod.cards.*;
import vexMod.events.*;
import vexMod.modifiers.NoRelicMode;
import vexMod.modifiers.ShiftingDeckMod;
import vexMod.monsters.*;
import vexMod.potions.*;
import vexMod.relics.*;
import vexMod.util.TextureLoader;
import vexMod.variables.DefaultSecondMagicNumber;

import java.util.List;
import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.language;

@SuppressWarnings("WeakerAccess")
@SpireInitializer
public class VexMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber,
        PostUpdateSubscriber,
        MaxHPChangeSubscriber,
        PostDungeonInitializeSubscriber,
        AddCustomModeModsSubscriber {
    public static final Logger logger = LogManager.getLogger(VexMod.class.getName());
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static final String ENABLE_MEME_CARDS = "enableMemes";
    public static final String BADGE_IMAGE = "vexModResources/images/Badge.png";
    private static final String MODNAME = "VexMod";
    private static final String AUTHOR = "DarkVexon";
    private static final String DESCRIPTION = "A major content mod.";
    public static Properties vexModDefaultSettings = new Properties();
    public static boolean enablePlaceholder = true;
    public static boolean enableMemes = true;
    private static String modID;


    public VexMod() {
        logger.info("baseMod hooks BOYS");

        BaseMod.subscribe(this);
        setModID("vexMod");

        logger.info("aand done with hooks");

        logger.info("settings time LOL");
        vexModDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "TRUE");
        vexModDefaultSettings.setProperty(ENABLE_MEME_CARDS, "TRUE");
        try {
            SpireConfig config = new SpireConfig("vexMod", "vexModConfig", vexModDefaultSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
            enableMemes = config.getBool(ENABLE_MEME_CARDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Mod settings concluded.");

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void setModID(String ID) {
        modID = ID;
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Here comes the vexMod. =========================");
        VexMod vexmod = new VexMod();
        logger.info("========================= vex has infiltrated your PC =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostInitialize() {
        logger.info("it's badge and options time");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);


        ModPanel settingsPanel = new ModPanel();

        String labelText1;

        if (language == Settings.GameLanguage.ZHS) {
            labelText1 = "是否参与推特互动？这将允许你的玩家名和游戏信息展示在@DBotling的推特上。";
        } else {
            labelText1 = "Opt-in to Twitter services? This will display your username and game info on Twitter at @DBotling.";
        }

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton(labelText1,
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enablePlaceholder, settingsPanel, (label) -> {
        }, (button) -> {
            enablePlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig("vexMod", "vexModConfig", vexModDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableNormalsButton);

        String labelText2;

        if (language == Settings.GameLanguage.ZHS) {
            labelText2 = "是否允许瞎搞 / 玩梗的卡牌 / 遗物出现在游戏中？（这些内容的平衡很可能没有好好考虑过）";
        } else {
            labelText2 = "Enable silly / meme cards and relics? (They are not very balanced.)";
        }

        ModLabeledToggleButton enableMemesButton = new ModLabeledToggleButton(labelText2,
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enableMemes, settingsPanel, (label) -> {
        }, (button) -> {
            enableMemes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("vexMod", "vexModConfig", vexModDefaultSettings);
                config.setBool(ENABLE_MEME_CARDS, enableMemes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableMemesButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        BaseMod.addEvent(GenieBoonEvent.ID, GenieBoonEvent.class, Exordium.ID);
        BaseMod.addEvent(TripleChoiceEvent.ID, TripleChoiceEvent.class);
        BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, TheCity.ID);
        BaseMod.addEvent(LookAheadEvent.ID, LookAheadEvent.class, Exordium.ID);
        BaseMod.addEvent(VoiceBoxEvent.ID, VoiceBoxEvent.class, TheCity.ID);
        BaseMod.addEvent(DeadIroncladEvent.ID, DeadIroncladEvent.class, TheCity.ID);
        BaseMod.addEvent(DeadSilentEvent.ID, DeadSilentEvent.class, TheCity.ID);
        BaseMod.addEvent(DeadDefectEvent.ID, DeadDefectEvent.class, TheCity.ID);
        BaseMod.addEvent(ChampiresEvent.ID, ChampiresEvent.class, TheCity.ID);
        BaseMod.addEvent(SerpentsRevenge.ID, SerpentsRevenge.class, TheCity.ID);
        BaseMod.addEvent(RareCardShrine.ID, RareCardShrine.class);
        BaseMod.addEvent(GorgonEncounterEvent.ID, GorgonEncounterEvent.class, TheCity.ID);
        BaseMod.addEvent(TweeterEvent.ID, TweeterEvent.class);
        BaseMod.addEvent(TweetThroughAges.ID, TweetThroughAges.class);
        BaseMod.addEvent(GrifterEvent.ID, GrifterEvent.class, TheCity.ID);
        BaseMod.addEvent(XCostLoverEvent.ID, XCostLoverEvent.class, Exordium.ID);


        receiveEditPotions();


        BaseMod.addMonster(Combatant.ID, "Mechanical Combatant", () -> new Combatant(-50.0F, 100.0F));
        BaseMod.addMonster(InfectionBeast.ID, "Infection Beast", () -> new InfectionBeast(-100.0F, 0.0F));
        BaseMod.addMonster(BombBelcher.ID, "Bomb Belcher", () -> new BombBelcher(0.0F, 100.0F));
        BaseMod.addMonster(EvilSsserpent.ID, "Ssserpent", () -> new EvilSsserpent(0.0F, 100.0F));
        BaseMod.addMonster(GildedGorgon.ID, "Gilded Gorgon", () -> new GildedGorgon(0.0F, 100.0F));
        BaseMod.addMonster(Grifter.ID, "Grifter", () -> new Grifter(-175.0F, 0.0F));
        BaseMod.addMonster(BeyondKing.ID, "King of the Beyond", () -> new BeyondKing(0.0F, 100.0F));
        BaseMod.addMonster(DaggerThrower.ID, "Dagger Pharaoh", () -> new DaggerThrower(0.0F, 100.0F));
        BaseMod.addMonster(LichLord.ID, () -> new MonsterGroup(new AbstractMonster[]{
                new LichLord(-200.0F, 0.0F),
                new LichPhylac(150.0F, 0.0F)
        }));


        if (language == Settings.GameLanguage.ENG) {
            BaseMod.addBoss(TheBeyond.ID, BeyondKing.ID, makeEventPath("beyondKing.png"), makeEventPath("beyondKingOutline.png"));
        }
        BaseMod.addBoss(Exordium.ID, DaggerThrower.ID, makeEventPath("daggerThrower.png"), makeEventPath("daggerThrowerOutline.png"));
        BaseMod.addBoss(TheCity.ID, LichLord.ID, makeEventPath("lichLord.png"), makeEventPath("lichLordOutline.png"));
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(Combatant.ID, 1.0F));
        BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo(InfectionBeast.ID, 1.0F));
        BaseMod.addEliteEncounter(TheBeyond.ID, new MonsterInfo(BombBelcher.ID, 1.0F));


        logger.info("My image and mod options done.");

    }

    public void receiveEditPotions() {
        logger.info("Potion edits begin.");

        BaseMod.addPotion(BlazePotion.class, Color.RED, Color.BLUE, Color.TEAL, BlazePotion.POTION_ID);
        BaseMod.addPotion(GoldPotion.class, Color.YELLOW, Color.GREEN, Color.BLACK, GoldPotion.POTION_ID);
        BaseMod.addPotion(LightningBottle.class, Color.YELLOW, Color.CYAN, Color.TEAL, LightningBottle.POTION_ID);
        BaseMod.addPotion(CameraPotion.class, Color.TEAL, Color.GOLD, Color.CORAL, CameraPotion.POTION_ID);
        BaseMod.addPotion(FrostBottle.class, Color.BLUE, Color.NAVY, Color.BLACK, FrostBottle.POTION_ID);
        BaseMod.addPotion(PlasmaBottle.class, Color.ORANGE, Color.CORAL, Color.OLIVE, PlasmaBottle.POTION_ID);

        logger.info("Potion edits concluded.");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("it's a me, relics");

        BaseMod.addRelic(new ColdYogurt(), RelicType.BLUE);
        BaseMod.addRelic(new CoolingFan(), RelicType.BLUE);
        BaseMod.addRelic(new CodeSmelter(), RelicType.BLUE);
        BaseMod.addRelic(new PowerCharger(), RelicType.BLUE);
        BaseMod.addRelic(new GazerBeam(), RelicType.BLUE);
        BaseMod.addRelic(new OrbOfGreed(), RelicType.BLUE);
        BaseMod.addRelic(new MemoryStick(), RelicType.GREEN);
        BaseMod.addRelic(new InvisibilityCloak(), RelicType.GREEN);
        BaseMod.addRelic(new SalvageTools(), RelicType.GREEN);
        BaseMod.addRelic(new RetaliationKit(), RelicType.GREEN);
        BaseMod.addRelic(new ThoughtMold(), RelicType.RED);
        BaseMod.addRelic(new ChargeUpper(), RelicType.RED);
        BaseMod.addRelic(new StrikeShooter(), RelicType.RED);
        BaseMod.addRelic(new CursedBlade(), RelicType.RED);
        BaseMod.addRelic(new ConsolationPrize(), RelicType.SHARED);
        BaseMod.addRelic(new PlagueVial(), RelicType.SHARED);
        BaseMod.addRelic(new ShiftingSkin(), RelicType.SHARED);
        BaseMod.addRelic(new ProtectorBot(), RelicType.SHARED);
        BaseMod.addRelic(new PainConverter(), RelicType.SHARED);
        BaseMod.addRelic(new BetterTron(), RelicType.SHARED);
        BaseMod.addRelic(new GhostlyGear(), RelicType.SHARED);
        BaseMod.addRelic(new CursedCompass(), RelicType.SHARED);
        BaseMod.addRelic(new Cooldron(), RelicType.SHARED);
        BaseMod.addRelic(new TanglingVine(), RelicType.SHARED);
        BaseMod.addRelic(new MindDevourer(), RelicType.SHARED);
        BaseMod.addRelic(new HappyDrink(), RelicType.SHARED);
        BaseMod.addRelic(new FocusSash(), RelicType.SHARED);
        BaseMod.addRelic(new BigBerry(), RelicType.SHARED);
        BaseMod.addRelic(new KeyChain(), RelicType.SHARED);
        BaseMod.addRelic(new CardConverter(), RelicType.SHARED);
        BaseMod.addRelic(new DrawConverter(), RelicType.SHARED);
        BaseMod.addRelic(new BrokenBowl(), RelicType.SHARED);
        BaseMod.addRelic(new PillTracker(), RelicType.SHARED);
        BaseMod.addRelic(new SlimeArmor(), RelicType.SHARED);
        BaseMod.addRelic(new ScavengeHelm(), RelicType.SHARED);
        BaseMod.addRelic(new BroadSword(), RelicType.SHARED);
        BaseMod.addRelic(new DoubleEdgedSword(), RelicType.SHARED);
        BaseMod.addRelic(new DrainingSword(), RelicType.SHARED);
        BaseMod.addRelic(new ImaginarySword(), RelicType.SHARED);
        BaseMod.addRelic(new ToySword(), RelicType.SHARED);
        BaseMod.addRelic(new RustySword(), RelicType.SHARED);
        BaseMod.addRelic(new InfernalSword(), RelicType.SHARED);
        BaseMod.addRelic(new VoiceBox(), RelicType.SHARED);
        BaseMod.addRelic(new RobotsGift(), RelicType.SHARED);
        BaseMod.addRelic(new MidasArmor(), RelicType.SHARED);
        BaseMod.addRelic(new TimeEaterHat(), RelicType.SHARED);
        BaseMod.addRelic(new BerrySword(), RelicType.SHARED);
        BaseMod.addRelic(new Dragonfruit(), RelicType.SHARED);
        BaseMod.addRelic(new RegenHeart(), RelicType.SHARED);
        BaseMod.addRelic(new LichBottle(), RelicType.SHARED);
        BaseMod.addRelic(new MallPass(), RelicType.SHARED);
        BaseMod.addRelic(new FloorLord(), RelicType.SHARED);
        BaseMod.addRelic(new HatredEngine(), RelicType.SHARED);
        BaseMod.addRelic(new EndlessSickness(), RelicType.SHARED);
        BaseMod.addRelic(new GoldBooster(), RelicType.SHARED);
        BaseMod.addRelic(new TreasureMap(), RelicType.SHARED);
        BaseMod.addRelic(new RandomRelic(), RelicType.SHARED);
        BaseMod.addRelic(new BerryBomb(), RelicType.SHARED);
        BaseMod.addRelic(new MadnessLens(), RelicType.SHARED);
        BaseMod.addRelic(new TimesightGlass(), RelicType.SHARED);
        BaseMod.addRelic(new PlasmaPancake(), RelicType.SHARED);
        BaseMod.addRelic(new ChompingNoodles(), RelicType.SHARED);
        BaseMod.addRelic(new RedPlottingStone(), RelicType.SHARED);
        BaseMod.addRelic(new TealTab(), RelicType.SHARED);
        BaseMod.addRelic(new DeviousBotling(), RelicType.SHARED);
        BaseMod.addRelic(new WellWornAnklet(), RelicType.SHARED);
        BaseMod.addRelic(new GorgonsHead(), RelicType.SHARED);
        BaseMod.addRelic(new ImprovementManual(), RelicType.SHARED);
        BaseMod.addRelic(new ThirteenHourStone(), RelicType.SHARED);
        BaseMod.addRelic(new TheWave(), RelicType.SHARED);
        BaseMod.addRelic(new LastWill(), RelicType.SHARED);
        BaseMod.addRelic(new Starfruit(), RelicType.SHARED);
        BaseMod.addRelic(new FallenStar(), RelicType.SHARED);
        BaseMod.addRelic(new PaidLearning(), RelicType.SHARED);
        BaseMod.addRelic(new PuzzleBox(), RelicType.SHARED);
        BaseMod.addRelic(new HeadHunter(), RelicType.SHARED);
        BaseMod.addRelic(new NotEnergy(), RelicType.SHARED);
        BaseMod.addRelic(new NewsTicker(), RelicType.SHARED);
        BaseMod.addRelic(new MagicMissile(), RelicType.SHARED);
        BaseMod.addRelic(new GildedClover(), RelicType.SHARED);
        BaseMod.addRelic(new FluxCapacitor(), RelicType.SHARED);
        BaseMod.addRelic(new StoryBook(), RelicType.SHARED);
        BaseMod.addRelic(new GrifterSatchel(), RelicType.SHARED);
        BaseMod.addRelic(new Pepega(), RelicType.SHARED);
        BaseMod.addRelic(new MegatonBomb(), RelicType.SHARED);
        BaseMod.addRelic(new BarristanHead(), RelicType.SHARED);
        BaseMod.addRelic(new NewClearReactor(), RelicType.SHARED);
        BaseMod.addRelic(new SpireShuffle(), RelicType.SHARED);

        UnlockTracker.markRelicAsSeen(ColdYogurt.ID);
        UnlockTracker.markRelicAsSeen(ConsolationPrize.ID);
        UnlockTracker.markRelicAsSeen(PlagueVial.ID);
        UnlockTracker.markRelicAsSeen(ShiftingSkin.ID);
        UnlockTracker.markRelicAsSeen(ProtectorBot.ID);
        UnlockTracker.markRelicAsSeen(PainConverter.ID);
        UnlockTracker.markRelicAsSeen(BetterTron.ID);
        UnlockTracker.markRelicAsSeen(GhostlyGear.ID);
        UnlockTracker.markRelicAsSeen(CursedCompass.ID);
        UnlockTracker.markRelicAsSeen(MemoryStick.ID);
        UnlockTracker.markRelicAsSeen(CoolingFan.ID);
        UnlockTracker.markRelicAsSeen(Cooldron.ID);
        UnlockTracker.markRelicAsSeen(InvisibilityCloak.ID);
        UnlockTracker.markRelicAsSeen(CodeSmelter.ID);
        UnlockTracker.markRelicAsSeen(TanglingVine.ID);
        UnlockTracker.markRelicAsSeen(MindDevourer.ID);
        UnlockTracker.markRelicAsSeen(HappyDrink.ID);
        UnlockTracker.markRelicAsSeen(SalvageTools.ID);
        UnlockTracker.markRelicAsSeen(ThoughtMold.ID);
        UnlockTracker.markRelicAsSeen(ChargeUpper.ID);
        UnlockTracker.markRelicAsSeen(FocusSash.ID);
        UnlockTracker.markRelicAsSeen(PowerCharger.ID);
        UnlockTracker.markRelicAsSeen(StrikeShooter.ID);
        UnlockTracker.markRelicAsSeen(BigBerry.ID);
        UnlockTracker.markRelicAsSeen(KeyChain.ID);
        UnlockTracker.markRelicAsSeen(CursedBlade.ID);
        UnlockTracker.markRelicAsSeen(CardConverter.ID);
        UnlockTracker.markRelicAsSeen(DrawConverter.ID);
        UnlockTracker.markRelicAsSeen(BrokenBowl.ID);
        UnlockTracker.markRelicAsSeen(PillTracker.ID);
        UnlockTracker.markRelicAsSeen(SlimeArmor.ID);
        UnlockTracker.markRelicAsSeen(ScavengeHelm.ID);
        UnlockTracker.markRelicAsSeen(BroadSword.ID);
        UnlockTracker.markRelicAsSeen(DoubleEdgedSword.ID);
        UnlockTracker.markRelicAsSeen(DrainingSword.ID);
        UnlockTracker.markRelicAsSeen(ImaginarySword.ID);
        UnlockTracker.markRelicAsSeen(RustySword.ID);
        UnlockTracker.markRelicAsSeen(ToySword.ID);
        UnlockTracker.markRelicAsSeen(RetaliationKit.ID);
        UnlockTracker.markRelicAsSeen(InfernalSword.ID);
        UnlockTracker.markRelicAsSeen(VoiceBox.ID);
        UnlockTracker.markRelicAsSeen(RobotsGift.ID);
        UnlockTracker.markRelicAsSeen(MidasArmor.ID);
        UnlockTracker.markRelicAsSeen(TimeEaterHat.ID);
        UnlockTracker.markRelicAsSeen(BerrySword.ID);
        UnlockTracker.markRelicAsSeen(Dragonfruit.ID);
        UnlockTracker.markRelicAsSeen(RegenHeart.ID);
        UnlockTracker.markRelicAsSeen(LichBottle.ID);
        UnlockTracker.markRelicAsSeen(MallPass.ID);
        UnlockTracker.markRelicAsSeen(FloorLord.ID);
        UnlockTracker.markRelicAsSeen(EndlessSickness.ID);
        UnlockTracker.markRelicAsSeen(HatredEngine.ID);
        UnlockTracker.markRelicAsSeen(GoldBooster.ID);
        UnlockTracker.markRelicAsSeen(TreasureMap.ID);
        UnlockTracker.markRelicAsSeen(RandomRelic.ID);
        UnlockTracker.markRelicAsSeen(BerryBomb.ID);
        UnlockTracker.markRelicAsSeen(GazerBeam.ID);
        UnlockTracker.markRelicAsSeen(MadnessLens.ID);
        UnlockTracker.markRelicAsSeen(TimesightGlass.ID);
        UnlockTracker.markRelicAsSeen(ChompingNoodles.ID);
        UnlockTracker.markRelicAsSeen(PlasmaPancake.ID);
        UnlockTracker.markRelicAsSeen(RedPlottingStone.ID);
        UnlockTracker.markRelicAsSeen(TealTab.ID);
        UnlockTracker.markRelicAsSeen(DeviousBotling.ID);
        UnlockTracker.markRelicAsSeen(WellWornAnklet.ID);
        UnlockTracker.markRelicAsSeen(GorgonsHead.ID);
        UnlockTracker.markRelicAsSeen(OrbOfGreed.ID);
        UnlockTracker.markRelicAsSeen(ImprovementManual.ID);
        UnlockTracker.markRelicAsSeen(ThirteenHourStone.ID);
        UnlockTracker.markRelicAsSeen(TheWave.ID);
        UnlockTracker.markRelicAsSeen(LastWill.ID);
        UnlockTracker.markRelicAsSeen(Starfruit.ID);
        UnlockTracker.markRelicAsSeen(FallenStar.ID);
        UnlockTracker.markRelicAsSeen(PaidLearning.ID);
        UnlockTracker.markRelicAsSeen(HeadHunter.ID);
        UnlockTracker.markRelicAsSeen(NotEnergy.ID);
        UnlockTracker.markRelicAsSeen(NewsTicker.ID);
        UnlockTracker.markRelicAsSeen(MagicMissile.ID);
        UnlockTracker.markRelicAsSeen(GildedClover.ID);
        UnlockTracker.markRelicAsSeen(FluxCapacitor.ID);
        UnlockTracker.markRelicAsSeen(StoryBook.ID);
        UnlockTracker.markRelicAsSeen(GrifterSatchel.ID);
        UnlockTracker.markRelicAsSeen(Pepega.ID);
        UnlockTracker.markRelicAsSeen(MegatonBomb.ID);
        UnlockTracker.markRelicAsSeen(BarristanHead.ID);
        UnlockTracker.markRelicAsSeen(NewClearReactor.ID);
        UnlockTracker.markRelicAsSeen(SpireShuffle.ID);

        logger.info("woo hoo relics be cool");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Variable time.");
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        logger.info("Variable added.");
        logger.info("AND NOW, CARDS");
        BaseMod.addCard(new BlockBeam());
        BaseMod.addCard(new CleverClash());
        BaseMod.addCard(new DeadlyDodge());
        BaseMod.addCard(new ReflexChannel());
        BaseMod.addCard(new Fear());
        BaseMod.addCard(new Greed());
        BaseMod.addCard(new StrikeStorm());
        BaseMod.addCard(new ChaoticReactor());
        BaseMod.addCard(new VenomSigh());
        BaseMod.addCard(new Overload());
        BaseMod.addCard(new CrumblingCrash());
        BaseMod.addCard(new TuneUp());
        BaseMod.addCard(new MidasTouch());
        BaseMod.addCard(new PotOfGreed());
        BaseMod.addCard(new BloodGuard());
        BaseMod.addCard(new Jackpot());
        BaseMod.addCard(new EntryPlan());
        BaseMod.addCard(new PrepareVictim());
        BaseMod.addCard(new ShockKick());
        BaseMod.addCard(new Taunt());
        BaseMod.addCard(new BandageWhip());
        BaseMod.addCard(new BeamPop());
        BaseMod.addCard(new ChargeBeam());
        BaseMod.addCard(new Devastation());
        BaseMod.addCard(new Elimination());
        BaseMod.addCard(new FullService());
        BaseMod.addCard(new PinpointKick());
        BaseMod.addCard(new Plotting());
        BaseMod.addCard(new ShadowStrike());
        BaseMod.addCard(new BattleStance());
        BaseMod.addCard(new CoinToss());
        BaseMod.addCard(new EvasiveThoughts());
        BaseMod.addCard(new GroundPound());
        BaseMod.addCard(new Hatred());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new MidnightStrike());
        BaseMod.addCard(new RocketPunch());
        BaseMod.addCard(new SharpOrbs());
        BaseMod.addCard(new Sloth());
        BaseMod.addCard(new BlazeBeam());
        BaseMod.addCard(new SystemUpdate());
        BaseMod.addCard(new Division());
        BaseMod.addCard(new Virus());
        BaseMod.addCard(new AntiqueFury());
        BaseMod.addCard(new OrbBoomerang());
        BaseMod.addCard(new DefensiveStance());
        BaseMod.addCard(new FightResponse());
        BaseMod.addCard(new Execute());
        BaseMod.addCard(new FaceSlap());
        BaseMod.addCard(new Gloat());
        BaseMod.addCard(new HeavySlash());
        BaseMod.addCard(new Insult());
        BaseMod.addCard(new StairwayStrike());
        BaseMod.addCard(new ScaledPoison());
        BaseMod.addCard(new ShieldSwipe());
        BaseMod.addCard(new GrowingStorm());
        BaseMod.addCard(new DarkConflux());
        BaseMod.addCard(new DiverseOrb());
        BaseMod.addCard(new BloodToGold());
        BaseMod.addCard(new GhostlyBlitz());
        BaseMod.addCard(new CurseBlast());
        BaseMod.addCard(new Weakness());
        BaseMod.addCard(new GoldenLightningCard());
        BaseMod.addCard(new NamedBlast());
        BaseMod.addCard(new CalendarSmash());
        BaseMod.addCard(new EmailVexCard());
        BaseMod.addCard(new TrainingStrike());
        BaseMod.addCard(new GorgonsGaze());
        BaseMod.addCard(new GorgonsGlare());
        BaseMod.addCard(new EyeBeam());
        BaseMod.addCard(new SnakeSkin());
        BaseMod.addCard(new CursedStrike());
        BaseMod.addCard(new StarBlast());
        BaseMod.addCard(new Stardash());
        BaseMod.addCard(new Deathsprout());
        BaseMod.addCard(new WeekCard());
        BaseMod.addCard(new UltimateCard());
        BaseMod.addCard(new WellTimedStrike());
        BaseMod.addCard(new VolumeVengeance());
        BaseMod.addCard(new ChimeraCard());
        BaseMod.addCard(new EvolveCard());
        BaseMod.addCard(new NiftyMoves());

        logger.info("WOO HOO CARDS I LOVE CARDS");
        UnlockTracker.unlockCard(BlockBeam.ID);
        UnlockTracker.unlockCard(CleverClash.ID);
        UnlockTracker.unlockCard(DeadlyDodge.ID);
        UnlockTracker.unlockCard(ReflexChannel.ID);
        UnlockTracker.unlockCard(Fear.ID);
        UnlockTracker.unlockCard(Greed.ID);
        UnlockTracker.unlockCard(StrikeStorm.ID);
        UnlockTracker.unlockCard(ChaoticReactor.ID);
        UnlockTracker.unlockCard(VenomSigh.ID);
        UnlockTracker.unlockCard(Overload.ID);
        UnlockTracker.unlockCard(CrumblingCrash.ID);
        UnlockTracker.unlockCard(TuneUp.ID);
        UnlockTracker.unlockCard(MidasTouch.ID);
        UnlockTracker.unlockCard(PotOfGreed.ID);
        UnlockTracker.unlockCard(BloodGuard.ID);
        UnlockTracker.unlockCard(Jackpot.ID);
        UnlockTracker.unlockCard(EntryPlan.ID);
        UnlockTracker.unlockCard(PrepareVictim.ID);
        UnlockTracker.unlockCard(ShockKick.ID);
        UnlockTracker.unlockCard(Taunt.ID);
        UnlockTracker.unlockCard(BandageWhip.ID);
        UnlockTracker.unlockCard(BeamPop.ID);
        UnlockTracker.unlockCard(ChargeBeam.ID);
        UnlockTracker.unlockCard(Devastation.ID);
        UnlockTracker.unlockCard(Elimination.ID);
        UnlockTracker.unlockCard(FullService.ID);
        UnlockTracker.unlockCard(PinpointKick.ID);
        UnlockTracker.unlockCard(Plotting.ID);
        UnlockTracker.unlockCard(ShadowStrike.ID);
        UnlockTracker.unlockCard(BattleStance.ID);
        UnlockTracker.unlockCard(CoinToss.ID);
        UnlockTracker.unlockCard(EvasiveThoughts.ID);
        UnlockTracker.unlockCard(GroundPound.ID);
        UnlockTracker.unlockCard(Hatred.ID);
        UnlockTracker.unlockCard(Hex.ID);
        UnlockTracker.unlockCard(MidnightStrike.ID);
        UnlockTracker.unlockCard(RocketPunch.ID);
        UnlockTracker.unlockCard(SharpOrbs.ID);
        UnlockTracker.unlockCard(Sloth.ID);
        UnlockTracker.unlockCard(BlazeBeam.ID);
        UnlockTracker.unlockCard(SystemUpdate.ID);
        UnlockTracker.unlockCard(Division.ID);
        UnlockTracker.unlockCard(Virus.ID);
        UnlockTracker.unlockCard(AntiqueFury.ID);
        UnlockTracker.unlockCard(OrbBoomerang.ID);
        UnlockTracker.unlockCard(DefensiveStance.ID);
        UnlockTracker.unlockCard(FightResponse.ID);
        UnlockTracker.unlockCard(Execute.ID);
        UnlockTracker.unlockCard(FaceSlap.ID);
        UnlockTracker.unlockCard(Gloat.ID);
        UnlockTracker.unlockCard(HeavySlash.ID);
        UnlockTracker.unlockCard(Insult.ID);
        UnlockTracker.unlockCard(StairwayStrike.ID);
        UnlockTracker.unlockCard(ScaledPoison.ID);
        UnlockTracker.unlockCard(ShieldSwipe.ID);
        UnlockTracker.unlockCard(GrowingStorm.ID);
        UnlockTracker.unlockCard(DarkConflux.ID);
        UnlockTracker.unlockCard(DiverseOrb.ID);
        UnlockTracker.unlockCard(BloodToGold.ID);
        UnlockTracker.unlockCard(GhostlyBlitz.ID);
        UnlockTracker.unlockCard(CurseBlast.ID);
        UnlockTracker.unlockCard(Weakness.ID);
        UnlockTracker.unlockCard(GoldenLightningCard.ID);
        UnlockTracker.unlockCard(NamedBlast.ID);
        UnlockTracker.unlockCard(CalendarSmash.ID);
        UnlockTracker.unlockCard(EmailVexCard.ID);
        UnlockTracker.unlockCard(TrainingStrike.ID);
        UnlockTracker.unlockCard(GorgonsGaze.ID);
        UnlockTracker.unlockCard(GorgonsGlare.ID);
        UnlockTracker.unlockCard(EyeBeam.ID);
        UnlockTracker.unlockCard(SnakeSkin.ID);
        UnlockTracker.unlockCard(CursedStrike.ID);
        UnlockTracker.unlockCard(StarBlast.ID);
        UnlockTracker.unlockCard(Stardash.ID);
        UnlockTracker.unlockCard(Deathsprout.ID);
        UnlockTracker.unlockCard(WeekCard.ID);
        UnlockTracker.unlockCard(UltimateCard.ID);
        UnlockTracker.unlockCard(WellTimedStrike.ID);
        UnlockTracker.unlockCard(VolumeVengeance.ID);
        UnlockTracker.unlockCard(ChimeraCard.ID);
        UnlockTracker.unlockCard(EvolveCard.ID);
        UnlockTracker.unlockCard(NiftyMoves.ID);

        logger.info("Alright, cards are in boys");
    }

    private String languageSupport() {
        if (language == Settings.GameLanguage.ZHS) {
            return "zhs";
        }
        return "eng";
    }

    @Override
    public void receiveEditStrings() {
        logger.info("And now, the strings!!!!!");

        String path = "Resources/localization/" + languageSupport() + "/";

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + path + "vexMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + path + "vexMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + path + "vexMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + path + "vexMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + path + "vexMod-Monster-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + path + "vexMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + path + "vexMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(RunModStrings.class,
                getModID() + path + "vexMod-RunMod-Strings.json");

        logger.info("Crazy, isn't it?");
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player == null) return;
        if (AbstractDungeon.player.hasRelic(BerryBomb.ID)) BerryBomb.relicBullshit();
        if (AbstractDungeon.player.hasRelic(RedPlottingStone.ID)) RedPlottingStone.FuckShitPoo();
        if (AbstractDungeon.player.hasRelic(PuzzleBox.ID)) PuzzleBox.relicBullshit();
        if (AbstractDungeon.player.hasRelic(FluxCapacitor.ID)) {
            if (AbstractDungeon.player.getRelic(FluxCapacitor.ID).counter == -2) {
                FluxCapacitor.relicBullshit();
            }
        }
        if (AbstractDungeon.player.hasRelic(MegatonBomb.ID)) MegatonBomb.relicBullshit();
    }

    @Override
    public int receiveMaxHPChange(int amount) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MidasArmor.ID)) {
            MidasArmor relic = (MidasArmor) AbstractDungeon.player.getRelic(MidasArmor.ID);
            amount = relic.onMaxHPChange();
        }
        return amount;
    }

    @Override
    public void receivePostDungeonInitialize() {
        if (CardCrawlGame.trial != null) {
            if (CardCrawlGame.trial.dailyModIDs().contains(ShiftingDeckMod.ID)) {
                AbstractDungeon.player.masterDeck.clear();
                for (int i = 0; i < 10; i++) {
                    AbstractDungeon.player.masterDeck.group.add(new ChimeraCard().makeCopy());
                }
            }
        }
        if (!VexMod.enablePlaceholder) {
            AbstractDungeon.removeCardFromPool(EmailVexCard.ID, EmailVexCard.NAME, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardColor.COLORLESS);
        }
        if (!VexMod.enableMemes) {
            AbstractDungeon.removeCardFromPool(EmailVexCard.ID, EmailVexCard.NAME, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(FullService.ID, FullService.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(CalendarSmash.ID, CalendarSmash.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(WeekCard.ID, WeekCard.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(CalendarSmash.ID, CalendarSmash.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(NamedBlast.ID, NamedBlast.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(WellTimedStrike.ID, WellTimedStrike.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(VolumeVengeance.ID, VolumeVengeance.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(ChimeraCard.ID, ChimeraCard.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
            AbstractDungeon.removeCardFromPool(Division.ID, Division.NAME, AbstractCard.CardRarity.RARE, AbstractCard.CardColor.COLORLESS);
        }
    }

    @Override
    public void receiveCustomModeMods(List<CustomMod> list) {
        list.add(new CustomMod(ShiftingDeckMod.ID, "b", true));
        list.add(new CustomMod(NoRelicMode.ID, "b", true));
    }

}

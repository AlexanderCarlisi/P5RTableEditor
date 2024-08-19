package com.p5rte.Utils;

public final class Enums {
    

    public static enum Arcana {
        Fool(1), Magician(2), Priestess(3), Empress(4), Emperor(5), Hierophant(6),
        Lovers(7), Chariot(8), Justice(9), Hermit(10), Fortune(11), Strength(12), Hanged(13),
        Death(14), Temperance(15), Devil(16), Tower(17), Star(18), Moon(19), Sun(20), Judgement(21),
        Aeon(22), Other(23), World(24), Faith(29), Councillor(30);

        public final int ID;

        private Arcana(int ID) {
            this.ID = ID;
        }

        public static Arcana getArcana(int id) {
            for (Arcana arcana : Arcana.values()) {
                if (arcana.ID == id) {
                    return arcana;
                }
            }
            return Other;
        }
    }


    public static enum SkillInheritance {
        None(0), Physical(1), Fire(2), Ice(3),
        Electric(4), Wind(5), Bless(6), Curse(7),
        Healing(8), Ailment(10), Almighty(12), RESERVE(13),
        Nuke(14), Psy(15);

        public final int ID;

        private SkillInheritance(int id) {
            this.ID = id;
        }

        public static SkillInheritance getSkillInheritance(int id) {
            for (SkillInheritance skillInheritance : SkillInheritance.values()) {
                if (skillInheritance.ID == id) {
                    return skillInheritance;
                }
            }
            return None;
        }
    }
    

    public static enum SkillLearnability {
        Nothing(0),
        Skill(1),
        Trait(3);

        public final int ID;

        private SkillLearnability(int id) {
            this.ID = id;
        }

        public static SkillLearnability getSkillLearnability(int id) {
            for (SkillLearnability skillLearnability : SkillLearnability.values()) {
                if (skillLearnability.ID == id) {
                    return skillLearnability;
                }
            }
            return Nothing;
        }
    }


    public enum AffinityDataIndex {
        DoubleAilmentChance,
        GuaranteeAilment,
        AilmentImmune,
        Resist,
        Weak,
        Drain,
        Repel,
        Block;

        public static AffinityDataIndex getAt(int index) {
            return AffinityDataIndex.values()[index];
        }
    }


    public enum AffinityIndex {
        Physical,
        Gun,
        Fire,
        Ice,
        Electric,
        Wind,
        Psy,
        Nuke,
        Bless,
        Curse,
        Almighty,
        Dizzy,
        Confuse,
        Fear,
        Forget,
        Hunger,
        Sleep,
        Rage,
        Despair,
        Brainwash;

        public static AffinityIndex getAt(int index) {
            return AffinityIndex.values()[index];
        }
    }


    public enum BitFlag {
        DLC(0),
        TreasureDemon(1),
        Party(4),
        Story(5),
        NotRegisterable(6),
        NotNormalFusion(8),
        Evolved(9);

        public final int INDEX;

        private BitFlag(int index) {
            this.INDEX = index;
        }
    }


    public enum EPartyMember {
        Protagonist,
        Ryuji,
        Morgana,
        Ann,
        Yusuke,
        Makoto,
        Haru,
        Futaba,
        Akechi,
        Kasumi,
        NoChange,
        Progtagonist_
    }


    public enum EPartyMemberPersona {
        CaptainKidd(202), SeitenTaisei(212), William(242),
        Zorro(203), Mercurius(213), Diego(243),
        Carmen(204), Hecate(214), Celestine(244),
        Goemon(205), KamuSusano_o(215), Gorokichi(245),
        Johanna(206), Anat(216), Agnes(246),
        Milady(207), Astarte(217), Lucy(247),
        Necronomicon(208), Prometheus(218), AlAzif(248),
        RobinHood(209), Loki(239), Hereward(249),
        CendrillonIntro(229), Cendrillon(240), Ella(250);

        public final int PERSONA_INDEX;

        private EPartyMemberPersona(int personaIndex) {
            this.PERSONA_INDEX = personaIndex;
        }
    }


    public enum ETrait {
        NoTrait, RESERVE148, RESERVE149, RESERVE150, RESERVE151, RESERVE152, Relentless, RESERVE153, RESERVE154, SaviorBloodline, GraceOfMother, RESERVE155, RESERVE156, RESERVE157, ReliefBloodline, AveMaria, RESERVE158, FoulStench, RESERVE159, RESERVE160, RESERVE161, RESERVE162, StaticElectricity1, GhostNest, ColdBlooded, RESERVE163, RESERVE164, RESERVE165, RESERVE166, RESERVE167, AllureOfWisdom, RESERVE168, RESERVE169, RESERVE170, RESERVE171, RESERVE172, RESERVE173, RESERVE174, FrenziedBull, RESERVE175, RESERVE176, RESERVE177, RESERVE178, RESERVE179, RESERVE180, RESERVE181, IntenseFocus, RESERVE182, RESERVE183, RESERVE184, RESERVE185, RESERVE186, RESERVE187, RESERVE188, MightyGaze, RESERVE189, RESERVE190, StrikingWeight, UndyingFury, RESERVE191, RESERVE192, RESERVE193, RetaliatingBody, InviolableBeauty, RESERVE194, PaganAllure, RESERVE195, RESERVE196, RESERVE197, RESERVE198, RareAntibody, Immunity, RESERVE199, SkillfulCombo, LinkedBloodline, RESERVE200, RESERVE201, RESERVE202, FoulOdor, ThermalConduct, RESERVE203, RESERVE204, RESERVE205, RESERVE206, RESERVE207, RESERVE208, PinchAnchor, VitalityOfTheTree, GloomyChild, RESERVE209, RESERVE210, RESERVE211, RESERVE212, RESERVE213, SinfulTechnique, UniversalLaw, RESERVE214, AilmentHunter, HollowJester, RESERVE215, RESERVE216, RESERVE217, DeathlyIllness, Omen, RESERVE218, RESERVE219, RESERVE220, RESERVE221, RESERVE222, RESERVE223, RESERVE224, RESERVE225, RESERVE226, RESERVE227, RESERVE228, RESERVE229, RESERVE230, RESERVE231, RESERVE232, RESERVE233, RESERVE234, RESERVE235, RESERVE236, RESERVE237, RESERVE238, RESERVE239, RESERVE240, RESERVE241, RESERVE242, ExplosiveScheme, RESERVE243, InfiniteScheme, RagingTemper, EccentricTemper, RESERVE244, ProudPresence, MajesticPresence, RESERVE245, MasteryOfMagic, PinnacleOfMagic, RESERVE246, ScoundrelEyes, UnparalleledEyes, RESERVE247, GaiaPact, GaiaBlessing, RESERVE248, IcyGlare, CoolCustomer, RESERVE249, TacticalSpirit, IngeniousSpirit, FlawlessSpirit, VeilOfMidnight, VeilOfSunrise, RESERVE250, HeatedBloodline, DrunkenPassion, FrigidBloodline, Cocytus, ElectricBloodline, BargainBolts, WindBloodline, VahanasWings, PsychicBloodline, ChiYousBlessing, AtomicBloodline, AtomicHellscape, BlessBloodline, MartyrsGift, CursedBloodline, MothersLament, RESERVE251, Gluttonmouth, DemonsBite, MouthofSavoring, Naranari, HallowedSpirit, PotentHypnosis, WealthOfLotus, InternalHypnosis, PositiveThoughts, DrainingMouth, RESERVE252, TagTeam, IronHeart, CrisisControl, BloodstainedEyes, WillOfTheSword, RESERVE253, CircleOfSadness, BolsteringForce, GodMaker, HazyPresence, CountryMaker, GraceOfOlive, JustDie, BlessSpirit, RESERVE254, RESERVE255, RESERVE256, RESERVE257, RESERVE258, RESERVE259, RESERVE260, RESERVE261, RESERVE262, RESERVE263, RESERVE264, RESERVE265, RESERVE266, RESERVE267, RESERVE268, RESERVE269, RESERVE270, RESERVE271, RESERVE272, RESERVE273, RESERVE274, RESERVE275, RESERVE276, RESERVE277, RESERVE278, RESERVE279, RESERVE280, RESERVE281, RESERVE282, RESERVE283, RESERVE284, RESERVE285, RESERVE286, RESERVE287, RESERVE288, RESERVE289, RESERVE290, RESERVE291, RESERVE292, RESERVE293, RESERVE294, RESERVE295, RESERVE296, RESERVE297, RESERVE298, RESERVE299, RESERVE300, RESERVE301, RESERVE302, RESERVE303, RESERVE304, RESERVE305, RESERVE306, RESERVE307, RESERVE308, RESERVE309, RESERVE310, RESERVE311, RESERVE312, RESERVE313, RESERVE314, RESERVE315, RESERVE316, RESERVE317, RESERVE318, RESERVE319, RESERVE320, RESERVE321, RESERVE322, RESERVE323, RESERVE324, RESERVE325, RESERVE326, RESERVE327, RESERVE328, RESERVE329, RESERVE330, RESERVE331, RESERVE332, RESERVE333, RESERVE334, RESERVE335, RESERVE336, UltimateVessel, UltimateVessel2, UltimateVessel3, UltimateVessel4, UltimateVessel5, UltimateVessel6, UltimateVessel7, UltimateVessel8, UltimateVessel9, RESERVE337, RESERVE338, RESERVE339, RESERVE340, RESERVE341, RESERVE342, RESERVE343, RESERVE344, RESERVE345, RESERVE346;

        public static ETrait getAt(int index) {
            return ETrait.values()[index];
        }
    }


    public enum ESkill {
        None, BLANK, BLANK2, BLANK3, BLANK4, BLANK5, BLANK6, BLANK7, BLANK8, BLANK9, Agi, Agilao, Agidyne, Maragi, Maragion, Maragidyne, Agi2, Maragi2, FireBall, RagingFlames, Bufu, Bufula, Bufudyne, Mabufu, Mabufula, Mabufudyne, Bufu2, Mabufu2, SnowBall, Megidolaon, Garu, Garula, Garudyne, Magaru, Magarula, Magarudyne, Garu2, GaleBall, BlessBall, CurseBall, Zio, Zionga, Ziodyne, Mazio, Mazionga, Maziodyne, Zio2, Mazio2, VoltBall, MakeitRain, Hama, Hamaon, Mahama, Mahamaon, Kouha, Kouga, Kougaon, Makouha, Makouga, Makougaon, Mudo, Mudoon, Mamudo, Mamudoon, Eiha, Eiga, Eigaon, Maeiha, Maeiga, Maeigaon, Megido, Megidola, Megidolaon2, Frei, Freila, Freidyne, Mafrei, Mafreila, Mafreidyne, NukeBall, Dazzler, NocturnalFlash, Pulinpa, Tentarafoo, EvilTouch, EvilSmile, Makajama, Makajamaon, FaminesBreath, FaminesScream, Dormina, Lullaby, Taunt, WageWar, OminousWords, AbysmalSurge, MarinKarin, BrainJack, TrappedRat, PsyBall, Selfdestruct, Selfdestruct2, Selfdestruct3, LifeDrain, SpiritDrain, LifeLeech, SpiritLeech, SpiritDrain2, NOTUSED, BLANK10, FoulBreath, StagnantAir, ReverseRub, GhastlyWail, NOTUSED2, Drain, Megidola2, Launch, SpecialFireworks, Drift, Inferno, BlazingHell, LowBurn, MedBurn, HighBurn, LowFreeze, MedFreeze, HighFreeze, LowShock, MedShock, DiamondDust, IceAge, HighShock, LowDizzy, MedDizzy, HighDizzy, LowConfuse, MedConfuse, HighConfuse, LowFear, PantaRhei, VacuumWave, MedFear, HighFear, LowForget, MedForget, HighForget, LowBrainwash, MedBrainwash, HighBrainwash, ThunderReign, WildThunder, LowSleep, MedSleep, HighSleep, LowRage, MedRage, HighRage, LowDespair, MedDespair, DivineJudgment, Samsara, HighDespair, LowAllAil, MidAllAil, HighAllAil, AdamSkill1, RevitalizeSoul, GrandPalm, FullForce, DemonicDecree, DieForMe, SupportPlus3, SupportPlus2, SupportPlus1, SupportRateUp, AtomicFlare, CosmicFlare, Mindfulness, Wakefulness, BlackViper, MorningStar, AbyssalEye, ChampionsCup, BleedingDryBrush, VaultGuardian, WingsOfWisdom, PresidentsInsight, GamblersForesight, TyrantsWill, Psi, Psio, Psiodyne, Mapsi, Mapsio, Mapsiodyne, AttackPosition, PsychoForce, PsychoBlast, UniversalPower, Lunge, AssaultDive, MegatonRaid, GodsHand, Lunge2, LuckyPunch, MiraclePunch, KillRush, GatlingBlow, PiercingStrike, Cleave, GiantSlice, BraveBlade, SwordDance, HolyBenevolence, HassouTobi, Ayamur, DeathScythe, UNUSED, NOTUSED3, CorneredFang, RisingSlash, DeadlyFury, NuclearCrush, Snap, TripleDown, OneshotKill, RiotGun, DoubleShot, OriginLight, VajraBlast, VorpalBlade, NOTUSED4, NOTUSED5, NOTUSED6, ViciousStrike, HeatWave, Gigantomachia, SwirlingPsychokinesis, TyrantsPurge, MassBall, Rampage, SwiftStrike, Deathbound, Agneyastra, NOTUSED7, Regeneration, RisingSlash2, DeadlyFury2, TyrantsJudgement, DoubleFangs, PowerSlash, ShapelessGuard, TempestSlash, MyriadSlashes, AmplifyForce, AmplifyMagic, RainingSeeds, EnergyStream, Flow, Sledgehammer, SkullCracker, TerrorClaw, Headbutt, StomachBlow, DreamNeedle, HystericalSlap, NegativePile, BrainShake, Attack2, FlashBomb, MindSlice, Bloodbath, MemoryBlow, InsatiableStrike, DorminRush, OniKagura, BadBeat, BrainBuster, Laevateinn, Handgun, Shotgun, Slingshot, MachineGun, AssaultRifle, Revolver, GrenadeLauncher, LaserGun, AntiqueRifle, TyrantStance, FightingSpirit, MiracleRush, Checkmate, HyakkaRyouran, HighEnergy, UltimateHealingSupport, LifeWall, RebellionBlade, Masquerade, GuidingTendril, Dia, Diarama, Diarahan, BraveStep, MarukiPunch, Media, Mediarama, Mediarahan, BrutalImpact, CursedStrike, Recarm, Samarecarm, Recarmdra, SleuthingInstinct, SleuthingMastery, AmritaDrop, AmritaShower, HolyStrike, Salvation, NuclearStrike, PsychokinesisStrike, TauntingAura, StormPunishment, Concealment, LightningPunishment, Patra, PunishingHail, EnergyShower, EnergyDrop, Baisudi, MePatra, Mabaisudi, ChargeBall, ConcentratedBall, InfernoPunishment, Tarukaja, Rakukaja, Sukukaja, HeatRiser, GunfirePunishment, Matarukaja, Marakukaja, Masukukaja, Thermopylae, GuillotinePunishment, Tarunda, Rakunda, Sukunda, Debilitate, QuadrupleSummon, Matarunda, Marakunda, Masukunda, Analysis, Analysis2, Dekunda, Dekaja, Explosion, Explosion2, SphynxSwipe, Charge, Concentrate, NoseDive, KillRewardUp, GuardRewardUp, Rebellion, Revolution, MakeitRain2, SpecialGuards, FakeArtistsGrace, Tetrakarn, Makarakarn, Tetraja, TasteofWrath, TrueFake, TetraBreak, MakaraBreak, KillshotofLove, NOTUSED8, NOTUSED9, FireWall, IceWall, ElecWall, WindWall, BLANK11, FireBreak, IceBreak, WindBreak, ElecBreak, BLANK12, Trafuri, Traesto, ActiveBarrier, NukeWall, PsyWall, NukeBreak, PsyBreak, BLANK13, BLANK14, Flow1, AlloutLv1, AlloutLv2, AlloutLv3, EmergencyEscape, Attack3, DownShot, Summon, CallforBackup, OutlawAttack, Barrage, FollowCrush, FollowClaw, FollowWhip, FollowBlade, FollowKnuckle, FollowAxe, FollowSaber, DisposeItem, HealEnemy, DeathDespair, Ally1FollowUp, Ally2FollowUp, Ally3FollowUp, Ally4FollowUp, Ally5FollowUp, Ally6FollowUp, Ally7FollowUp, PowerUpEnemy, PowerUpEnemy2, PowerUpEnemy3, StealInfo, SupMatarukaja, SupMarakukaja, SupMasukukaja, SupAllKaja, SupCharge, SupHP30, SupSP10, SupEscapeRoute, SupThirdEye, SubrecoverHP, SubrecoverSP, Analysis3, DeepAnalysis, FullAnalysis, AllOutAttack, DustFlurry, TongueWhip, WhipStrike, LustfulSlurp, LibidoBoost, GoldenKnife, Lick, GoldMedalSpike, VolleyballAssault, Maelstrom, FlameDance, SilentSnowscape, Thunderclap, Maeiga2, HungerAll, OcularVulcan, MissileParty, FearGas, SuperVIPForm, MarchofthePiggy, SphinxSwipe, WingBlast, RapidAscent, SphinxDive, DreadfulScream, Bite, Restore, TheArtistsGrace, WorkOrder, SacrificeOrder, SelflessDevotion, Penalty, CoinAttack, SeveringSlash, GatlingGun, BerserkerDance, RouletteHP, RouletteSP, RouletteMoney, RouletteAid1, RouletteAid2, RouletteAid3, BeastKingsWrath, HuntingStance, ArmofDestruction, GryphonsBreath, RoyalWingBeam, CannonFire, CannonBarrage, UnholyConvergence, PyramidBlast, TyrantsFist, TyrantsGlare, TyrantsWave, FigSeed, Medicine, Ointment, AntibioticGel, LifeStone, Bead, ValueMedicine, MedicalKit, MakaLeaf, BeadChain, SoulDrop, SnuffSoul, ChewingSoul, SoulFood, RevivalBead, BalmofLife, RoyelJelly, OrganicHerb, Disclose, Tranquilizer, KopiLuwak, GohoM, VanishBall, Soma, AmritaSoda, Hiranya, MuscleDrink, OddMorsel, RancidGravy, PlumeofDusk, MagicMirror, PhysicalMirror, UniverseRing, Sleipnir, ObsidianMirror, PurifyingWater, PurifyingSalt, Firecracker, SanzunTama, IceCube, DryIce, Pinwheel, Yashichi, BallLightning, TeslaCoil, SmartBomb, SegamiRice, CursePaper, FlameMagatama, WindMagatama, FreezeMagatama, BoltMagatama, Homunculus, RemovalPotion, KougaBall, MakouhaBall, EigaBall, MaeigaBall, PsioBall, MapsiBall, FreilaBall, MafreiBall, NukeMagatama, PsyMagatama, BlessMagatama, CurseMagatama, Cooking, Snack, Shoes1, Shoes2, Shoes3, JuiceBar1, JuiceBar2, JuiceBar3, JuiceBar4, EnergyDrink1, EnergyDrink2, EnergyDrink3, Soda1, Soda2, Soda3, Soda4, Ration1, Ration2, Ration3, DrugStore1, DrugStore2, DrugStore3, DrugStore4, DrugStore5, DrugStore6, DrugStore7, DrugStore8, DrugStore9, DrugStore10, DrugStore11, DrugStore12, SpecialCoffee1, SpecialCoffee2, DoubleFangs2, TwinsDownAttack, CaroRod, CaroAttack, Megidolaon3, RaysofControl, RaysofControl2, RaysofControl3, RaysofControl4, ArrowofLight, DiffractionArrow, LightEdge, GatheringLight, EternalLight, HolyChange, DistortionWave, ToughLaw, FrailLaw, NewCreation, DistortionSurge, ArrowofLight2, ManifestSword, ManifestGun, ManifestBell, SwordofJudgment, CapitalPunishment, ManifestBook, DistortedLust, Gospel, DistortedWrath, DistortedVanity, SongofSalvation, WrathofGod, DistortedGluttony, DistortedEnvy, SongofPlacation, DivineApex, RaysofControl5, DistortedAvarice, WillofthePeople, RaysofControl6, MoralSupport, ActiveSupport, MentalHack, EmergencyShift, FinalGuard, PositionHack, HighAnalyze, TreasureSkimmer, SubrecoverHPEX, SubrecoverSPEX, BigBangTreat, Explosion3, Explosion4, Explosion5, BigBangOrder, RouletteTime, TripUpper, LustSphere, DownShot2, DownShot3, DownShot4, ElectroAttack, SinfulShell, FollowBlack, Summon2, MadaraMegido, CurryRaku, EnemyBenefit, Diarahan2, CurrySuku, DecoyBall, SuperDecoyBall, MetabolicWave, Laevateinn2, Desperation, CallofChaos, BigBangChallenge, GrailLight, Megidola3, RageTransmission, HundredSlaps, DistortedPride, DivinePunishment, WillofthePeople2, WindCutter, ShootUp, ExecutivePunch, Megidolaon4, VorpalBlade2, Megidolaon5, BigBangBurger, EarthBurger, MarsBurger, MoonBurger, SaturnBurger, JustineAttack, CarolineAttack, CurryMakara, CurryTetra, CurryEndure, CurryCharge, CurryConcentrate, CurryHeatRiser, MagicWall, Cadenza, CrossSlash, DoorofHades, MagatsuMandala, ShiningArrows, BeastWeaver, Titanomachia, AbyssalWings, Oratario, MyriadTruths, NeoCadenza, AkashaArts, PhantomShow, ConfuseBall, BaptismBall, ExorcismBall, Megido2, Megidola4, Megidolaon6, Slam, MegatonRaid2, OneshotKill2, Inferno2, DiamondDust2, EternalRadiance, TyrantChaos, NewCurry1, NewCurry2, RevivAll, RevivAllZ, DarkAkechiForpursuing, DarkAkechiForGunpursuing, WildTalk, BatonPass, PunkTalk, Pickpocket, HarisenRecovery, Protect, GirlTalk, CrocodileTears, SexyTechnique, DetectiveTalk, ArtistTalk, Negotiating, Fundraising, Manipulation, MindControl, CharismaSpeech, BrainiacTalk, Sabaki, KakoiKuzushi, Touryou, TogoSystem, BulletHail, WarningShot, CelebTalk, KittyTalk, MarinKarin2, Womanizing, IndignantRevenge, HealingPower, HealingPower2, Taunt2, IridescentChange, BraveBlade2, AssaultDive2, TerrorClaw2, Bufudyne2, Psiodyne2, Mazionga2, Maziodyne2, Maeiga3, Maeigaon2, AdamSkill5, AdamSkill6, VorpalBlade3, MonaRyujiUnisonAttack, MonaAnnUnisonAttack, MonaHaruUnisonAttack, YusukeAnnUnisonAttack, RyujiYusukeUnisonAttack, RyujiMakotoUnisonAttack, ProtagAkechiUnisonAttack, MakotoHaruUnisonAttack, AkechiUnisonAttack, ProtagKasumiUnisonAttack, MonaRyujiUnisonAttack2, MonaAnnUnisonAttack2, MonaHaruUnisonAttack2, YusukeAnnUnisonAttack2, RyujiYusukeUnisonAttack2, RyujiMakotoUnisonAttack2, ProtagAkechiUnisonAttack2, MakotoHaruUnisonAttack2, AkechiUnisonAttack2, ProtagKasumiUnisonAttack2, Counter, Counterstrike, HighCounter, ResistBurn, NullBurn, Endure, EnduringSoul, ResistFreeze, NullFreeze, SurvivalTrick, DodgeFire, EvadeFire, DodgeIce, EvadeIce, DodgeWind, EvadeWind, DodgeElec, EvadeElec, DodgePhys, EvadePhys, FireBoost, FireAmp, IceBoost, IceAmp, WindBoost, WindAmp, ElecBoost, ElecAmp, AngelicGrace, DivineGrace, Regenerate1, Regenerate2, Regenerate3, DodgeBless, DodgeCurse, Invigorate1, Invigorate2, Invigorate3, EvadeBless, EvadeCurse, AttackMaster, AutoMataru, ResistShock, DefenseMaster, AutoMaraku, NullShock, SpeedMaster, AutoMasuku, ResistHunger, NullHunger, FastHeal, InstaHeal, ArmsMaster, SpellMaster, RageAttackUp, SharpStudent, AptPupil, AliDance, FirmStance, Plus50EXP, LifeAid, VictoryCry, Growth1, Growth2, Growth3, UnshakenWill, NullBlessInstaKill, BatonPass2, SoulTouch, KakoiKuzushi2, ResistFire, NullFire, RepelFire, DrainFire, NullCurseInstaKill, ResistIce, NullIce, RepelIce, DrainIce, Plus15EXP, ResistWind, NullWind, RepelWind, DrainWind, AllOutAttackBoost, ResistElec, NullElec, RepelElec, DrainElec, MoneyBoost, ResistBless, NullBless, RepelBless, DrainBless, Hide, ResistCurse, NullCurse, RepelCurse, DrainCurse, LifeBoost, ResistPhys, NullPhys, RepelPhys, DrainPhys, NullBlessCurse, AilmentBoost, HamaBoost, MudoBoost, GunAccuracyPlus5, SamuraiSpirit, DizzyBoost, ConfuseBoost, FearBoost, ForgetBoost, SleepBoost, RageBoost, DespairBoost, KuzunohaEmblem, BrainwashBoost, CriticalRateUpHigh, ResistDizzy, ResistConfuse, ResistFear, ResistForget, ResistSleep, ResistRage, ResistDespair, FusionAccidentUp, ResistBrainwash, TyrantsMind, NullDizzy, NullConfuse, NullFear, NullForget, NullSleep, NullRage, NullDespair, HolyWhisper, NullBrainwash, HolyEmbrace, BurnBoost, FreezeBoost, ShockBoost, BLANK15, FortifiedMoxy, AdverseResolve, LastStand, HeatUp, BLANK16, TouchnGo, ClimateDecorum, AmbientAid, RESERVE, Snipe, Cripple, TriggerHappy, ResistNuke, NullNuke, RepelNuke, DrainNuke, RESERVE2, ResistPsy, NullPsy, RepelPsy, DrainPsy, RESERVE3, NukeBoost, NukeAmp, RESERVE4, PsyBoost, PsyAmp, SexyTechnique2, DodgeNuke, EvadeNuke, Detox, DodgePsy, EvadePsy, Detox2, BlessBoost, BlessAmp, RESERVE5, CurseBoost, CurseAmp, NotFoundByEnemy, MagicAbility, FortifySpirit, AlmightyBoost, AlmightyAmp, ZenithDefense, SoulChain, VanityCopy, GluttonousSnuff, SlothDefense, BrushOfVanity, LifeRise, ManaRise, SoulTouch2, VictoryCry2, TraitDLCForBitedown, BLANK17, AilmentEffectUp, AilmentEffectUpPlus, InstakillSPHealLow, InstakillSPHealMid, InstakillSPHealHigh, TechnicalEffectUp, TechnicalEffectUpPlus, LowHPAttackUp, LowHPAttackUpPlus, WEAKHitEffectUp, WEAKHitEffectUpPlus, NullInstaKill, HPCostDown10, HPCostDown25, SPCostDown10, SPCostDown25, HealMagicUp10, HealMagicUp25, Chanceof0HPCost, Chanceof0SPCost, TargetATKUp, TargetATKUpPlus, AllCritsNoEvasion, HealCostDown25, HealCostDown10, ATKUpAimDown, AilmentSuccessUp, BLANK18, FourAffinityBoost, ThreeAffinityLightBoost, SupportTurnExtend, InstaKillUp, InstaKillUpPlus, LifeBonus, LifeGain, LifeSurge, ManaBonus, ManaGain, ManaSurge, CriticalEffectUp, CriticalEffectUpPlus, HitDamageDoubled, AllTargetATKUp, AllTargetATKUpPlus, AutoBarrier, BackupSupport, AbsoluteEscape, ShieldofLoyalty, AllAmp, BLANK19, BLANK20, BLANK21, BLANK22, BLANK23, BLANK24, BLANK25, BLANK26;

        public static ESkill getAt(int index) {
            return ESkill.values()[index];
        }
    }
}
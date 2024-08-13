package com.p5rte.Utils;

import java.util.HashMap;
import java.util.Map;

public final class Enums {
    

    public static enum Arcana {
        Fool(1),
        Magician(2),
        Priestess(3),
        Empress(4),
        Emperor(5),
        Hierophant(6),
        Lovers(7),
        Chariot(8),
        Justice(9),
        Hermit(10),
        Fortune(11),
        Strength(12),
        Hanged(13),
        Death(14),
        Temperance(15),
        Devil(16),
        Tower(17),
        Star(18),
        Moon(19),
        Sun(20),
        Judgement(21),
        Aeon(22),
        Other(23),
        World(24),
        Faith(29),
        Councillor(30);

        public final int ID;

        private Arcana(int id) {
            this.ID = id;
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
        None(0),
        Physical(1),
        Fire(2),
        Ice(3),
        Electric(4),
        Wind(5),
        Bless(6),
        Curse(7),
        Healing(8),
        Ailment(10),
        Almighty(12),
        RESERVE(13),
        Nuke(14),
        Psy(15);

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


    public enum ESkill {
        // Don't look cover your eyes.
        None(0), BLANK(1), BLANK2(2), BLANK3(3), BLANK4(4), BLANK5(5), BLANK6(6), BLANK7(7), BLANK8(8), BLANK9(9), Agi(10), Agilao(11), Agidyne(12), Maragi(13), Maragion(14), Maragidyne(15), Agi2(16), Maragi2(17), FireBall(18), RagingFlames(19), Bufu(20), Bufula(21), Bufudyne(22), Mabufu(23), Mabufula(24), Mabufudyne(25), Bufu2(26), Mabufu2(27), SnowBall(28), Megidolaon(29), Garu(30), Garula(31), Garudyne(32), Magaru(33), Magarula(34), Magarudyne(35), Garu2(36), GaleBall(37), BlessBall(38), CurseBall(39), Zio(40), Zionga(41), Ziodyne(42), Mazio(43), Mazionga(44), Maziodyne(45), Zio2(46), Mazio2(47), VoltBall(48), MakeitRain(49), Hama(50), Hamaon(51), Mahama(52), Mahamaon(53), Kouha(54), Kouga(55), Kougaon(56), Makouha(57), Makouga(58), Makougaon(59), Mudo(60), Mudoon(61), Mamudo(62), Mamudoon(63), Eiha(64), Eiga(65), Eigaon(66), Maeiha(67), Maeiga(68), Maeigaon(69), Megido(70), Megidola(71), Megidolaon2(72), Frei(73), Freila(74), Freidyne(75), Mafrei(76), Mafreila(77), Mafreidyne(78), NukeBall(79), Dazzler(80), NocturnalFlash(81), Pulinpa(82), Tentarafoo(83), EvilTouch(84), EvilSmile(85), Makajama(86), Makajamaon(87), FaminesBreath(88), FaminesScream(89), Dormina(90), Lullaby(91), Taunt(92), WageWar(93), OminousWords(94), AbysmalSurge(95), MarinKarin(96), BrainJack(97), TrappedRat(98), PsyBall(99), Selfdestruct(100), Selfdestruct2(101), Selfdestruct3(102), LifeDrain(103), SpiritDrain(104), LifeLeech(105), SpiritLeech(106), SpiritDrain2(107), NOTUSED(108), BLANK10(109), FoulBreath(110), StagnantAir(111), ReverseRub(112), GhastlyWail(113), NOTUSED2(114), Drain(115), Megidola2(116), Launch(117), SpecialFireworks(118), Drift(119), Inferno(120), BlazingHell(121), LowBurn(122), MedBurn(123), HighBurn(124), LowFreeze(125), MedFreeze(126), HighFreeze(127), LowShock(128), MedShock(129), DiamondDust(130), IceAge(131), HighShock(132), LowDizzy(133), MedDizzy(134), HighDizzy(135), LowConfuse(136), MedConfuse(137), HighConfuse(138), LowFear(139), PantaRhei(140), VacuumWave(141), MedFear(142), HighFear(143), LowForget(144), MedForget(145), HighForget(146), LowBrainwash(147), MedBrainwash(148), HighBrainwash(149), ThunderReign(150), WildThunder(151), LowSleep(152), MedSleep(153), HighSleep(154), LowRage(155), MedRage(156), HighRage(157), LowDespair(158), MedDespair(159), DivineJudgment(160), Samsara(161), HighDespair(162), LowAllAil(163), MidAllAil(164), HighAllAil(165), AdamSkill1(166), RevitalizeSoul(167), GrandPalm(168), FullForce(169), DemonicDecree(170), DieForMe(171), SupportPlus3(172), SupportPlus2(173), SupportPlus1(174), SupportRateUp(175), AtomicFlare(176), CosmicFlare(177), Mindfulness(178), Wakefulness(179), BlackViper(180), MorningStar(181), AbyssalEye(182), ChampionsCup(183), BleedingDryBrush(184), VaultGuardian(185), WingsOfWisdom(186), PresidentsInsight(187), GamblersForesight(188), TyrantsWill(189), Psi(190), Psio(191), Psiodyne(192), Mapsi(193), Mapsio(194), Mapsiodyne(195), AttackPosition(196), PsychoForce(197), PsychoBlast(198), UniversalPower(199), Lunge(200), AssaultDive(201), MegatonRaid(202), GodsHand(203), Lunge2(204), LuckyPunch(205), MiraclePunch(206), KillRush(207), GatlingBlow(208), PiercingStrike(209), Cleave(210), GiantSlice(211), BraveBlade(212), SwordDance(213), HolyBenevolence(214), HassouTobi(215), Ayamur(216), DeathScythe(217), UNUSED(218), NOTUSED3(219), CorneredFang(220), RisingSlash(221), DeadlyFury(222), NuclearCrush(223), Snap(224), TripleDown(225), OneshotKill(226), RiotGun(227), DoubleShot(228), OriginLight(229), VajraBlast(230), VorpalBlade(231), NOTUSED4(232), NOTUSED5(233), NOTUSED6(234), ViciousStrike(235), HeatWave(236), Gigantomachia(237), SwirlingPsychokinesis(238), TyrantsPurge(239), MassBall(240), Rampage(241), SwiftStrike(242), Deathbound(243), Agneyastra(244), NOTUSED7(245), Regeneration(246), RisingSlash2(247), DeadlyFury2(248), TyrantsJudgement(249), DoubleFangs(250), PowerSlash(251), ShapelessGuard(252), TempestSlash(253), MyriadSlashes(254), AmplifyForce(255), AmplifyMagic(256), RainingSeeds(257), EnergyStream(258), Flow(259), Sledgehammer(260), SkullCracker(261), TerrorClaw(262), Headbutt(263), StomachBlow(264), DreamNeedle(265), HystericalSlap(266), NegativePile(267), BrainShake(268), Attack2(269), FlashBomb(270), MindSlice(271), Bloodbath(272), MemoryBlow(273), InsatiableStrike(274), DorminRush(275), OniKagura(276), BadBeat(277), BrainBuster(278), Laevateinn(279), Handgun(280), Shotgun(281), Slingshot(282), MachineGun(283), AssaultRifle(284), Revolver(285), GrenadeLauncher(286), LaserGun(287), AntiqueRifle(288), TyrantStance(289), FightingSpirit(290), MiracleRush(291), Checkmate(292), HyakkaRyouran(293), HighEnergy(294), UltimateHealingSupport(295), LifeWall(296), RebellionBlade(297), Masquerade(298), GuidingTendril(299), Dia(300), Diarama(301), Diarahan(302), BraveStep(303), MarukiPunch(304), Media(305), Mediarama(306), Mediarahan(307), BrutalImpact(308), CursedStrike(309), Recarm(310), Samarecarm(311), Recarmdra(312), SleuthingInstinct(313), SleuthingMastery(314), AmritaDrop(315), AmritaShower(316), HolyStrike(317), Salvation(318), NuclearStrike(319), PsychokinesisStrike(320), TauntingAura(321), StormPunishment(322), Concealment(323), LightningPunishment(324), Patra(325), PunishingHail(326), EnergyShower(327), EnergyDrop(328), Baisudi(329), MePatra(330), Mabaisudi(331), ChargeBall(332), ConcentratedBall(333), InfernoPunishment(334), Tarukaja(335), Rakukaja(336), Sukukaja(337), HeatRiser(338), GunfirePunishment(339), Matarukaja(340), Marakukaja(341), Masukukaja(342), Thermopylae(343), GuillotinePunishment(344), Tarunda(345), Rakunda(346), Sukunda(347), Debilitate(348), QuadrupleSummon(349), Matarunda(350), Marakunda(351), Masukunda(352), Analysis(353), Analysis2(354), Dekunda(355), Dekaja(356), Explosion(357), Explosion2(358), SphynxSwipe(359), Charge(360), Concentrate(361), NoseDive(362), KillRewardUp(363), GuardRewardUp(364), Rebellion(365), Revolution(366), MakeitRain2(367), SpecialGuards(368), FakeArtistsGrace(369), Tetrakarn(370), Makarakarn(371), Tetraja(372), TasteofWrath(373), TrueFake(374), TetraBreak(375), MakaraBreak(376), KillshotofLove(377), NOTUSED8(378), NOTUSED9(379), FireWall(380), IceWall(381), ElecWall(382), WindWall(383), BLANK11(384), FireBreak(385), IceBreak(386), WindBreak(387), ElecBreak(388), BLANK12(389), Trafuri(390), Traesto(391), ActiveBarrier(392), NukeWall(393), PsyWall(394), NukeBreak(395), PsyBreak(396), BLANK13(397), BLANK14(398), Flow1(399), AlloutLv1(400), AlloutLv2(401), AlloutLv3(402), EmergencyEscape(403), Attack3(404), DownShot(405), Summon(406), CallforBackup(407), OutlawAttack(408), Barrage(409), FollowCrush(410), FollowClaw(411), FollowWhip(412), FollowBlade(413), FollowKnuckle(414), FollowAxe(415), FollowSaber(416), DisposeItem(417), HealEnemy(418), DeathDespair(419), Ally1FollowUp(420), Ally2FollowUp(421), Ally3FollowUp(422), Ally4FollowUp(423), Ally5FollowUp(424), Ally6FollowUp(425), Ally7FollowUp(426), PowerUpEnemy(427), PowerUpEnemy2(428), PowerUpEnemy3(429), StealInfo(430), SupMatarukaja(431), SupMarakukaja(432), SupMasukukaja(433), SupAllKaja(434), SupCharge(435), SupHP30(436), SupSP10(437), SupEscapeRoute(438), SupThirdEye(439), SubrecoverHP(440), SubrecoverSP(441), Analysis3(442), DeepAnalysis(443), FullAnalysis(444), AllOutAttack(445), DustFlurry(446), TongueWhip(447), WhipStrike(448), LustfulSlurp(449), LibidoBoost(450), GoldenKnife(451), Lick(452), GoldMedalSpike(453), VolleyballAssault(454), Maelstrom(455), FlameDance(456), SilentSnowscape(457), Thunderclap(458), Maeiga2(459), HungerAll(460), OcularVulcan(461), MissileParty(462), FearGas(463), SuperVIPForm(464), MarchofthePiggy(465), SphinxSwipe(466), WingBlast(467), RapidAscent(468), SphinxDive(469), DreadfulScream(470), Bite(471), Restore(472), TheArtistsGrace(473), WorkOrder(474), SacrificeOrder(475), SelflessDevotion(476), Penalty(477), CoinAttack(478), SeveringSlash(479), GatlingGun(480), BerserkerDance(481), RouletteHP(482), RouletteSP(483), RouletteMoney(484), RouletteAid1(485), RouletteAid2(486), RouletteAid3(487), BeastKingsWrath(488), HuntingStance(489), ArmofDestruction(490), GryphonsBreath(491), RoyalWingBeam(492), CannonFire(493), CannonBarrage(494), UnholyConvergence(495), PyramidBlast(496), TyrantsFist(497), TyrantsGlare(498), TyrantsWave(499), FigSeed(500), Medicine(501), Ointment(502), AntibioticGel(503), LifeStone(504), Bead(505), ValueMedicine(506), MedicalKit(507), MakaLeaf(508), BeadChain(509), SoulDrop(510), SnuffSoul(511), ChewingSoul(512), SoulFood(513), RevivalBead(514), BalmofLife(515), RoyelJelly(516), OrganicHerb(517), Disclose(518), Tranquilizer(519), KopiLuwak(520), GohoM(521), VanishBall(522), Soma(523), AmritaSoda(524), Hiranya(525), MuscleDrink(526), OddMorsel(527), RancidGravy(528), PlumeofDusk(529), MagicMirror(530), PhysicalMirror(531), UniverseRing(532), Sleipnir(533), ObsidianMirror(534), PurifyingWater(535), PurifyingSalt(536), Firecracker(537), SanzunTama(538), IceCube(539), DryIce(540), Pinwheel(541), Yashichi(542), BallLightning(543), TeslaCoil(544), SmartBomb(545), SegamiRice(546), CursePaper(547), FlameMagatama(548), WindMagatama(549), FreezeMagatama(550), BoltMagatama(551), Homunculus(552), RemovalPotion(553), KougaBall(554), MakouhaBall(555), EigaBall(556), MaeigaBall(557), PsioBall(558), MapsiBall(559), FreilaBall(560), MafreiBall(561), NukeMagatama(562), PsyMagatama(563), BlessMagatama(564), CurseMagatama(565), Cooking(566), Snack(567), Shoes1(568), Shoes2(569), Shoes3(570), JuiceBar1(571), JuiceBar2(572), JuiceBar3(573), JuiceBar4(574), EnergyDrink1(575), EnergyDrink2(576), EnergyDrink3(577), Soda1(578), Soda2(579), Soda3(580), Soda4(581), Ration1(582), Ration2(583), Ration3(584), DrugStore1(585), DrugStore2(586), DrugStore3(587), DrugStore4(588), DrugStore5(589), DrugStore6(590), DrugStore7(591), DrugStore8(592), DrugStore9(593), DrugStore10(594), DrugStore11(595), DrugStore12(596), SpecialCoffee1(597), SpecialCoffee2(598), DoubleFangs2(599), TwinsDownAttack(600), CaroRod(601), CaroAttack(602), Megidolaon3(603), RaysofControl(604), RaysofControl2(605), RaysofControl3(606), RaysofControl4(607), ArrowofLight(608), DiffractionArrow(609), LightEdge(610), GatheringLight(611), EternalLight(612), HolyChange(613), DistortionWave(614), ToughLaw(615), FrailLaw(616), NewCreation(617), DistortionSurge(618), ArrowofLight2(619), ManifestSword(620), ManifestGun(621), ManifestBell(622), SwordofJudgment(623), CapitalPunishment(624), ManifestBook(625), DistortedLust(626), Gospel(627), DistortedWrath(628), DistortedVanity(629), SongofSalvation(630), WrathofGod(631), DistortedGluttony(632), DistortedEnvy(633), SongofPlacation(634), DivineApex(635), RaysofControl5(636), DistortedAvarice(637), WillofthePeople(638), RaysofControl6(639), MoralSupport(640), ActiveSupport(641), MentalHack(642), EmergencyShift(643), FinalGuard(644), PositionHack(645), HighAnalyze(646), TreasureSkimmer(647), SubrecoverHPEX(648), SubrecoverSPEX(649), BigBangTreat(650), Explosion3(651), Explosion4(652), Explosion5(653), BigBangOrder(654), RouletteTime(655), TripUpper(656), LustSphere(657), DownShot2(658), DownShot3(659), DownShot4(660), ElectroAttack(661), SinfulShell(662), FollowBlack(663), Summon2(664), MadaraMegido(665), CurryRaku(666), EnemyBenefit(667), Diarahan2(668), CurrySuku(669), DecoyBall(670), SuperDecoyBall(671), MetabolicWave(672), Laevateinn2(673), Desperation(674), CallofChaos(675), BigBangChallenge(676), GrailLight(677), Megidola3(678), RageTransmission(679), HundredSlaps(680), DistortedPride(681), DivinePunishment(682), WillofthePeople2(683), WindCutter(684), ShootUp(685), ExecutivePunch(686), Megidolaon4(687), VorpalBlade2(688), Megidolaon5(689), BigBangBurger(690), EarthBurger(691), MarsBurger(692), MoonBurger(693), SaturnBurger(694), JustineAttack(695), CarolineAttack(696), CurryMakara(697), CurryTetra(698), CurryEndure(699), CurryCharge(700), CurryConcentrate(701), CurryHeatRiser(702), MagicWall(703), Cadenza(704), CrossSlash(705), DoorofHades(706), MagatsuMandala(707), ShiningArrows(708), BeastWeaver(709), Titanomachia(710), AbyssalWings(711), Oratario(712), MyriadTruths(713), NeoCadenza(714), AkashaArts(715), PhantomShow(716), ConfuseBall(717), BaptismBall(718), ExorcismBall(719), Megido2(720), Megidola4(721), Megidolaon6(722), Slam(723), MegatonRaid2(724), OneshotKill2(725), Inferno2(726), DiamondDust2(727), EternalRadiance(728), TyrantChaos(729), NewCurry1(730), NewCurry2(731), RevivAll(732), RevivAllZ(733), DarkAkechiForpursuing(734), DarkAkechiForGunpursuing(735), WildTalk(736), BatonPass(737), PunkTalk(738), Pickpocket(739), HarisenRecovery(740), Protect(741), GirlTalk(742), CrocodileTears(743), SexyTechnique(744), DetectiveTalk(745), ArtistTalk(746), Negotiating(747), Fundraising(748), Manipulation(749), MindControl(750), CharismaSpeech(751), BrainiacTalk(752), Sabaki(753), KakoiKuzushi(754), Touryou(755), TogoSystem(756), BulletHail(757), WarningShot(758), CelebTalk(759), KittyTalk(760), MarinKarin2(761), Womanizing(762), IndignantRevenge(763), HealingPower(764), HealingPower2(765), Taunt2(766), IridescentChange(767), BraveBlade2(768), AssaultDive2(769), TerrorClaw2(770), Bufudyne2(771), Psiodyne2(772), Mazionga2(773), Maziodyne2(774), Maeiga3(775), Maeigaon2(776), AdamSkill5(777), AdamSkill6(778), VorpalBlade3(779), MonaRyujiUnisonAttack(780), MonaAnnUnisonAttack(781), MonaHaruUnisonAttack(782), YusukeAnnUnisonAttack(783), RyujiYusukeUnisonAttack(784), RyujiMakotoUnisonAttack(785), ProtagAkechiUnisonAttack(786), MakotoHaruUnisonAttack(787), AkechiUnisonAttack(788), ProtagKasumiUnisonAttack(789), MonaRyujiUnisonAttack2(790), MonaAnnUnisonAttack2(791), MonaHaruUnisonAttack2(792), YusukeAnnUnisonAttack2(793), RyujiYusukeUnisonAttack2(794), RyujiMakotoUnisonAttack2(795), ProtagAkechiUnisonAttack2(796), MakotoHaruUnisonAttack2(797), AkechiUnisonAttack2(798), ProtagKasumiUnisonAttack2(799), Counter(800), Counterstrike(801), HighCounter(802), ResistBurn(803), NullBurn(804), Endure(805), EnduringSoul(806), ResistFreeze(807), NullFreeze(808), SurvivalTrick(809), DodgeFire(810), EvadeFire(811), DodgeIce(812), EvadeIce(813), DodgeWind(814), EvadeWind(815), DodgeElec(816), EvadeElec(817), DodgePhys(818), EvadePhys(819), FireBoost(820), FireAmp(821), IceBoost(822), IceAmp(823), WindBoost(824), WindAmp(825), ElecBoost(826), ElecAmp(827), AngelicGrace(828), DivineGrace(829), Regenerate1(830), Regenerate2(831), Regenerate3(832), DodgeBless(833), DodgeCurse(834), Invigorate1(835), Invigorate2(836), Invigorate3(837), EvadeBless(838), EvadeCurse(839), AttackMaster(840), AutoMataru(841), ResistShock(842), DefenseMaster(843), AutoMaraku(844), NullShock(845), SpeedMaster(846), AutoMasuku(847), ResistHunger(848), NullHunger(849), FastHeal(850), InstaHeal(851), ArmsMaster(852), SpellMaster(853), RageAttackUp(854), SharpStudent(855), AptPupil(856), AliDance(857), FirmStance(858), Plus50EXP(859), LifeAid(860), VictoryCry(861), Growth1(862), Growth2(863), Growth3(864), UnshakenWill(865), NullBlessInstaKill(866), BatonPass2(867), SoulTouch(868), KakoiKuzushi2(869), ResistFire(870), NullFire(871), RepelFire(872), DrainFire(873), NullCurseInstaKill(874), ResistIce(875), NullIce(876), RepelIce(877), DrainIce(878), Plus15EXP(879), ResistWind(880), NullWind(881), RepelWind(882), DrainWind(883), AllOutAttackBoost(884), ResistElec(885), NullElec(886), RepelElec(887), DrainElec(888), MoneyBoost(889), ResistBless(890), NullBless(891), RepelBless(892), DrainBless(893), Hide(894), ResistCurse(895), NullCurse(896), RepelCurse(897), DrainCurse(898), LifeBoost(899), ResistPhys(900), NullPhys(901), RepelPhys(902), DrainPhys(903), NullBlessCurse(904), AilmentBoost(905), HamaBoost(906), MudoBoost(907), GunAccuracyPlus5(908), SamuraiSpirit(909), DizzyBoost(910), ConfuseBoost(911), FearBoost(912), ForgetBoost(913), SleepBoost(914), RageBoost(915), DespairBoost(916), KuzunohaEmblem(917), BrainwashBoost(918), CriticalRateUpHigh(919), ResistDizzy(920), ResistConfuse(921), ResistFear(922), ResistForget(923), ResistSleep(924), ResistRage(925), ResistDespair(926), FusionAccidentUp(927), ResistBrainwash(928), TyrantsMind(929), NullDizzy(930), NullConfuse(931), NullFear(932), NullForget(933), NullSleep(934), NullRage(935), NullDespair(936), HolyWhisper(937), NullBrainwash(938), HolyEmbrace(939), BurnBoost(940), FreezeBoost(941), ShockBoost(942), BLANK15(943), FortifiedMoxy(944), AdverseResolve(945), LastStand(946), HeatUp(947), BLANK16(948), TouchnGo(949), ClimateDecorum(950), AmbientAid(951), RESERVE(952), Snipe(953), Cripple(954), TriggerHappy(955), ResistNuke(956), NullNuke(957), RepelNuke(958), DrainNuke(959), RESERVE2(960), ResistPsy(961), NullPsy(962), RepelPsy(963), DrainPsy(964), RESERVE3(965), NukeBoost(966), NukeAmp(967), RESERVE4(968), PsyBoost(969), PsyAmp(970), SexyTechnique2(971), DodgeNuke(972), EvadeNuke(973), Detox(974), DodgePsy(975), EvadePsy(976), Detox2(977), BlessBoost(978), BlessAmp(979), RESERVE5(980), CurseBoost(981), CurseAmp(982), NotFoundByEnemy(983), MagicAbility(984), FortifySpirit(985), AlmightyBoost(986), AlmightyAmp(987), ZenithDefense(988), SoulChain(989), VanityCopy(990), GluttonousSnuff(991), SlothDefense(992), BrushOfVanity(993), LifeRise(994), ManaRise(995), SoulTouch2(996), VictoryCry2(997), TraitDLCForBitedown(998), BLANK17(999), AilmentEffectUp(1000), AilmentEffectUpPlus(1001), InstakillSPHealLow(1002), InstakillSPHealMid(1003), InstakillSPHealHigh(1004), TechnicalEffectUp(1005), TechnicalEffectUpPlus(1006), LowHPAttackUp(1007), LowHPAttackUpPlus(1008), WEAKHitEffectUp(1009), WEAKHitEffectUpPlus(1010), NullInstaKill(1011), HPCostDown10(1012), HPCostDown25(1013), SPCostDown10(1014), SPCostDown25(1015), HealMagicUp10(1016), HealMagicUp25(1017), Chanceof0HPCost(1018), Chanceof0SPCost(1019), TargetATKUp(1020), TargetATKUpPlus(1021), AllCritsNoEvasion(1022), HealCostDown25(1023), HealCostDown10(1024), ATKUpAimDown(1025), AilmentSuccessUp(1026), BLANK18(1027), FourAffinityBoost(1028), ThreeAffinityLightBoost(1029), SupportTurnExtend(1030), InstaKillUp(1031), InstaKillUpPlus(1032), LifeBonus(1033), LifeGain(1034), LifeSurge(1035), ManaBonus(1036), ManaGain(1037), ManaSurge(1038), CriticalEffectUp(1039), CriticalEffectUpPlus(1040), HitDamageDoubled(1041), AllTargetATKUp(1042), AllTargetATKUpPlus(1043), AutoBarrier(1044), BackupSupport(1045), AbsoluteEscape(1046), ShieldofLoyalty(1047), AllAmp(1048), BLANK19(1049), BLANK20(1050), BLANK21(1051), BLANK22(1052), BLANK23(1053), BLANK24(1054), BLANK25(1055), BLANK26(1056);

        public final int ID;

        private ESkill(int id) {
            this.ID = id;
        }

        private static final Map<Integer, ESkill> idToSkillIDMap = new HashMap<>();

        static {
            for (ESkill eskill : ESkill.values()) {
                idToSkillIDMap.put(eskill.ID, eskill);
            }
        }

        public static ESkill getESkill(int id) {
            return idToSkillIDMap.getOrDefault(id, None);
        }
    }
}
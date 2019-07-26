-- ########################################################################## --
-- #  Internal main mapscript                                               # --
-- #  --------------------------------------------------------------------  # --
-- #    Author:   totalwarANGEL                                             # --
-- ########################################################################## --

gvWeatherGenerator = {
    ["SetupNormalWeatherGfxSet"]    = {RainProp = 55, SnowProp = 60},
    ["SetupHighlandWeatherGfxSet"]  = {RainProp = 70, SnowProp = 70},
    ["SetupMediteranWeatherGfxSet"] = {RainProp = 30, SnowProp = 10},
    ["SetupEvelanceWeatherGfxSet"]  = {RainProp = 30, SnowProp = 10},
    ["SetupSteppeWeatherGfxSet"]    = {RainProp = 15, SnowProp = 0},
    ["SetupMoorWeatherGfxSet"]      = {RainProp = 85, SnowProp = 0},

    MaxDuration  = 120,
    MinDuration  = 30,
    StatesAmount = 30,
    LastState    = 1;
};

gvPlayerColorMapping = {
    ["DEFAULT_COLOR"] = -1,
    ["PLAYER_COLOR"] = 1,
    ["NEPHILIM_COLOR"] = 2,
    ["FRIENDLY_COLOR1"] = 3,
    ["FRIENDLY_COLOR2"] = 4,
    ["ENEMY_COLOR2"] = 5,
    ["MERCENARY_COLOR"] = 6,
    ["ENEMY_COLOR3"] = 7,
    ["FARMER_COLOR"] = 8,
    ["EVIL_GOVERNOR_COLOR"] = 9,
    ["TRADER_COLOR"] = 10,
    ["NPC_COLOR"] = 11,
    ["KERBEROS_COLOR"] = 12,
    ["ENEMY_COLOR1"] = 13,
    ["ROBBERS_COLOR"] = 14,
    ["SAINT_COLOR"] = 15,
    ["FRIENDLY_COLOR3"] = 16,
};

QUEST_ASSISTENT_MAP_SETTINGS_DATA

-- Begin extra 3 stuff ------------------------------------------------------ --

localscript = {
	-- extra 3 config
	extra3config = {
		-- set these to true if you want to load your own versions
		doNotLoadMcbTrigger = false, --  trigger fix (functions as direct arguments, tables/functions as parameters, catching errors)
		doNotLoadFramework2 = false, -- Utility for map loading / savegames, including autosaves
		doNotLoadS5Hook = false, -- injects asm code to make C++ features accessible
		doNotLoadMetatableFix = false, -- tool to make metatables savegame compartible
		doNotLoadS5HookLoader = false, -- starts S5Hook at map start (cf. FirstMapAction) and after loading
		doNotLoadMcbEMan = false, -- lib to manipulate entities / entitytypes based on S5Hook
		doNotLoadMemoryManipulation = false,
		doNotLoadMcbAnim = false, -- comfort to execut custom entity animations
		doNotLoadInterfaceRescale = false, -- Loads a script to resize the interface correctly
		doNotLoadBuildRotation = false, -- enables to place rotated buildings (requires dll inject)
		doNotLoadWheelScroll = false, -- script support for scrolling with mouse wheel (requires dll inject)
		doNotLoadWallBuild = false, -- support script to build walls
		doNotLoadSerfBuildList = false, -- enables serfs to remember build/repair jobs
		doNotLoadSyncedOptions = false, -- synchronized ruleselection screen
		doNotLoadMpRuleset = false, -- mp ruleset
		doNotLoadHQDefenses = false, -- script support for headquarter defenses
		doNotLoadMcbTraderoute = false, -- tradecarts that drop their load when killed
		doNotLoadMcbMarket = false, -- market rework that uses tradecarts
		doNotLoadMcbWeather = false, -- random weather and multigfx sets
		doNotLoadMcbRecruitment = false, -- single leader upgrade / recruitable CU leaders / find buttons fix / optional alternative recruitment
		doNotLoadLuaTaskType = false, -- allows lua callback on invalid tasks (requires dll inject)
		doNotLoadStatisticsManipulation = false, -- allows to manipulate game statistics (requires dll inject)
		doNotLoadPolygon = false, -- simple polygons with point contain and distance tests
		doNotLoadMcbBrief = false, -- briefing rework with better camera control and Umlaute string formatting
		doNotLoadThiefGUI = false, -- adds invisibility progress bar and allows thieves to throw away ther stolen resources
		doNotLoadMcbQuestGUI = false, -- quest gui rework
		doNotLoadMcbTriggerExtHurtEntity = false, -- fixes LOGIC_EVENT_ENTITY_HURT_ENTITY trigger not being called if attacker is invalid
		doNotLoadHurtProjectileFix = false, -- fixes cannon projectiles not caring about armor/damageclass
		doNotLoadKeyTriggerHandle = false, -- allows multiple hook key triggers
		doNotLoadCNetEventCallback = false, -- reading and writing of CNetEvents as callback
	},
	
	-- sets player colors (call InitPlayerColorMapping if you change them after map start)
	playerColors = {
		[1] = 1,
		[2] = 2,
		[3] = 3,
		[4] = 4,
		[5] = 5,
		[6] = 6,
		[7] = 7,
		[8] = 8,
	},
	
	mcbMarketExits = nil, -- replace with a table of entities/positions to enable market rework
	mcbRecruitmentNoFreeUpgradeOnResearch = false, -- set to true to disable auto troop upgrade on tech research
	mcbBriefWaitForGUI = false, -- set to true if you want mcbBrief to wait for you to load a modified gui xml
	mpSyncerTributePlayer = 8, -- player which mcbMPSyncer uses for sync tributes
	
	-- mp config: mpNumberOfPlayers=nil -> sp
	mpNumberOfPlayers = nil,
	mpRulesetConfig = nil, -- use a custom ruleset config
};

for k, v in pairs(Global_MapConfigurationData.Players) do
    if gvPlayerColorMapping[v.Color] and gvPlayerColorMapping[v.Color] ~= -1 then
        localscript.playerColors[k] = gvPlayerColorMapping[v.Color];
    end
end

Script.Load(Folders.MapTools.."extra3loader.lua");

-- function that gets called after the map got loaded for the first time and all extra3 initializations are done.
function localscript.fma()
    Score.Player[0] = {};
    Score.Player[0]["buildings"] = 0;
    Score.Player[0]["all"] = 0;
    GenerateWeather();
    QuestSystemBehavior:PrepareQuestSystem();
    if Global_MapConfigurationData.Debug[1] or Global_MapConfigurationData.Debug[2] or Global_MapConfigurationData.Debug[3] or Global_MapConfigurationData.Debug[4] then
        QuestSystemDebug:Activate(unpack(Global_MapConfigurationData.Debug));
    end
    CreateQuests();
    PreperationDone();
end

-- function that gets called after the rule selection got closed (only if it got used)
function localscript.onRuleSelectionClosed()
	
end

-- function that gets called after the peacetime is over (only if the mpruleset has a peacetime option)
function localscript.onPeaceTimeEnd()
	
end

-- function that gets called when the mpSyncer is ready (all players connected) (immediately called in SP)
function localscript.onSyncerReady()
	
end

-- function that gets called after the map got loaded, but before extra3 initialization is done.
-- use it to create leaders with troops and walls
function localscript.fmaPreConfig()
	
end

-- End extra 3 stuff -------------------------------------------------------- --

--
-- Setzt die im Assistenten eingestellten diplomatischen Beziehungen für
-- den menschlichen Spieler. Außerdem werden die Namen der Parteien gesetzt.
--
function InitDiplomacy()
    for k, v in pairs(Global_MapConfigurationData.Players) do
        for i= 1, table.getn(v.Diplomacy), 1 do
            if v.Diplomacy[i][1] ~= k then
                local DiplomacyStateName = "Neutral";
                if (v.Diplomacy[i][2] == 2) then
                    DiplomacyStateName = "Friendly";
                elseif (v.Diplomacy[i][2] == 3) then
                    DiplomacyStateName = "Hostile";
                end
                _G["Set" ..DiplomacyStateName](k, v.Diplomacy[i][1]);
            end
        end
        if v.Name ~= "" then
            SetPlayerName(k, v.Name);
        end
    end
	
end

--
-- Gibt dem menschlichen Spieler die eingestellten Startrohstoffe.
--
function InitResources()
    Tools.GiveResouces(gvMission.PlayerID, unpack(Global_MapConfigurationData.Resources));
end

--
-- Setzt den Status von Technologien zu spielbeginn.
--
function InitTechnologies()
	
end

--
-- Ruft das im Assistenten eingestellte Wetterset auf.
--
function InitWeatherGfxSets()
    -- Extra 3
    if mcbWeather then
        local gfxnames = {
            ["SetupNormalWeatherGfxSet"]    = mcbWeather.set.normal,
            ["SetupHighlandWeatherGfxSet"]  = mcbWeather.set.highland,
            ["SetupMediteranWeatherGfxSet"] = mcbWeather.set.mediterranean,
            ["SetupEvelanceWeatherGfxSet"]  = mcbWeather.set.evelance,
            ["SetupMoorWeatherGfxSet"]      = mcbWeather.set.moor,
        };
        mcbWeather.setGFXSet(gfxnames[Global_MapConfigurationData.WeatherSet] or mcbWeather.set.normal);
    -- Extra 1/2
    else
        _G[Global_MapConfigurationData.WeatherSet]();
    end
end

---
-- Startet Wetterperioden zu Spielbeginn.
--
function InitWeather()
end

--
-- Startet alle Prozesse, die zu Beginn der Mission aktiviert werden müssen.
--
if not gvKeyBindings_RotateBackCameraFlag then
    function FirstMapAction()
        Score.Player[0] = {};
        Score.Player[0]["buildings"] = 0;
        Score.Player[0]["all"] = 0;
        
        math.randomseed(XGUIEng.GetSystemTime());
        GenerateWeather();
        
        QuestSystemBehavior:PrepareQuestSystem();
        if Global_MapConfigurationData.Debug[1] or Global_MapConfigurationData.Debug[2] or Global_MapConfigurationData.Debug[3] or Global_MapConfigurationData.Debug[4] then
            QuestSystemDebug:Activate(unpack(Global_MapConfigurationData.Debug));
        end
        CreateQuests();
        PreperationDone();
    end
end

--
-- Erzeugt zufälliges Wetter für das ausgewählte Wetterset.
--
function GenerateWeather()
    if Global_MapConfigurationData.RandomizeWeather then
        local WeatherData = gvWeatherGenerator[Global_MapConfigurationData.WeatherSet];
        for i= 1, gvWeatherGenerator.StatesAmount, 1 do
            local Length = math.random(gvWeatherGenerator.MinDuration, gvWeatherGenerator.MaxDuration);
            if i == 1 then
                gvWeatherGenerator.LastState = 1;
                AddPeriodicSummer(Length);
            else 
                if WeatherData.RainProp > 0 and math.random(1, 100) <= WeatherData.RainProp then
                    if WeatherData.SnowProp > 0 and gvWeatherGenerator.LastState > 1 and math.random(1, 100) <= WeatherData.SnowProp then
                        gvWeatherGenerator.LastState = 3;
                        AddPeriodicWinter(Length);
                    else
                        gvWeatherGenerator.LastState = 2;
                        AddPeriodicRain(Length);
                    end
                else
                    gvWeatherGenerator.LastState = 1;
                    AddPeriodicSummer(Length);
                end
            end
        end
    end
end
--
-- Hier werden die im Assistenten angelegten Quests definiert und gestartet.
--
function CreateQuests()
    QUEST_ASSISTENT_GENERATED_QUESTS
    for k, v in pairs(Global_QuestsToGenerate) do
        local Behaviors = {}
        for i= 1, table.getn(v), 1 do
            local CurrentBehaviorData = copy(v[i]);
            local CurrentBehaviorName = table.remove(CurrentBehaviorData, 1);
            table.insert(Behaviors, _G[CurrentBehaviorName](unpack(CurrentBehaviorData)));
        end
        local Description;
        if v.Visible then
            Description = {
                Title = v.Title,
                Text  = v.Text,
                Type  = _G[v.Type],
                Info  = 1
            }
        end
        CreateQuest {
            Name        = v.Name,
            Description = Description,
            Receiver    = v.Receiver,
            Time        = v.Time,
            unpack(Behaviors)
        };
    end
end

-- -------------------------------------------------------------------------- --

QUEST_ASSISTENT_GENERATED_BRIEFINGS
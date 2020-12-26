-- Include globals
Script.Load("data/script/maptools/main.lua");
Script.Load("data/script/maptools/mapeditortools.lua");

-- Load library
gvBasePath = "data/maps/externalmap/qsb/";
Script.Load(gvBasePath.. "core/oop.lua");
Script.Load(gvBasePath.. "core/questtools.lua");
Script.Load(gvBasePath.. "core/questsync.lua");
Script.Load(gvBasePath.. "core/bugfixes.lua");
Script.Load(gvBasePath.. "core/questsystem.lua");
Script.Load(gvBasePath.. "core/questbriefing.lua");
Script.Load(gvBasePath.. "core/questdebug.lua");
Script.Load(gvBasePath.. "lib/libloader.lua");
Script.Load(gvBasePath.. "ext/extraloader.lua");
Script.Load(gvBasePath.. "questbehavior.lua");
Script.Load(gvBasePath.. "treasure.lua");

-- Include main mapscript
Script.Load(gvBasePath.. "behaviors.lua");
Script.Load(gvBasePath.. "mainmapscript.lua");

--
-- Füge in dieser Funktion deine eigenen Inhalte hinzu. Diese Funktion wird
-- aufgerufen, sobald alle Voreinstellungen gemacht wurden.
--
function PreperationDone()

end

-- Platz für deine Funktionen...


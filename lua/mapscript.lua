-- Include globals
Script.Load("data/script/maptools/main.lua");
Script.Load("data/script/maptools/mapeditortools.lua");

-- Load library
gvBasePath = "data/maps/externalmap/";
Script.Load(gvBasePath.. "qsb/core/oop.lua");
Script.Load(gvBasePath.. "qsb/core/questtools.lua");
Script.Load(gvBasePath.. "qsb/core/questsync.lua");
Script.Load(gvBasePath.. "qsb/core/bugfixes.lua");
Script.Load(gvBasePath.. "qsb/core/questsystem.lua");
Script.Load(gvBasePath.. "qsb/core/questbriefing.lua");
Script.Load(gvBasePath.. "qsb/core/questdebug.lua");
Script.Load(gvBasePath.. "qsb/lib/libloader.lua");
Script.Load(gvBasePath.. "qsb/ext/extraloader.lua");
Script.Load(gvBasePath.. "qsb/questbehavior.lua");
Script.Load(gvBasePath.. "qsb/treasure.lua");

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


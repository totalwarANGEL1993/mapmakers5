
package twa.siedelwood.s5.mapmaker.service.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class replaces the tokens inside the internal and external map script
 * with the data from the assistant.
 * 
 * @author totalwarANGEL
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapScriptBuilder
{
	private QuestCollection quests;

	private BriefingCollection briefings;

	private MapData mapData;

	/**
	 * Overwrites the map name and the map description if set.
	 * @param filepath Path to info.xml
	 * @param name Map name
	 * @param desc Map description
	 * @return New file content
	 */
	public String replaceMapDescriptionInInfoFile(final String filepath, String name, String desc) {
		List<String> lines;
		try
		{
			lines = Files.readAllLines(Paths.get(filepath), Charset.forName("UTF-8"));
			final StringBuilder info = new StringBuilder();
			for (int i = 0; i < lines.size(); i++)
			{
				String lineToAppend = lines.get(i);
				// Name
				if (name != null && !name.equals("") && Pattern.matches("^.*<Name>.*</Name>.*$", lineToAppend)) {
					lineToAppend = lineToAppend.replaceAll("^.*<Name>.*</Name>.*$", "<Name>" +name+ "</Name>");
				}
				// Description
				if (desc != null && !desc.equals("") && Pattern.matches("^.*<Desc>.*</Desc>.*$", lineToAppend)) {
					lineToAppend = lineToAppend.replaceAll("^.*<Desc>.*</Desc>.*$", "<Desc>" +desc+ "</Desc>");
				}
				info.append(lineToAppend).append("\n");
			}
			return info.toString();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads a template and replaces all tokens with the appropriate contents.
	 * 
	 * @param filepath Template to load
	 * @return Lua string
	 */
	public String replaceTokensInMapscript(final String filepath)
	{
		List<String> lines;
		try
		{
			lines = Files.readAllLines(Paths.get(filepath), Charset.forName("UTF-8"));
			final StringBuilder mapScript = new StringBuilder();
			for (int i = 0; i < lines.size(); i++)
			{
				String lineToAppend = lines.get(i);
				lineToAppend = replaceQuestToken(lineToAppend);
				lineToAppend = replaceBriefingToken(lineToAppend);
				lineToAppend = replaceMapSettingsToken(lineToAppend);
				mapScript.append(lineToAppend).append("\n");
			}
			return mapScript.toString();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Replaces the quest token with the generated briefings.
	 * 
	 * @param lineToAppend Line token is in
	 * @return Lua string
	 */
	private String replaceQuestToken(String lineToAppend)
	{
		if (lineToAppend.contains("QUEST_ASSISTENT_GENERATED_QUESTS"))
		{
			lineToAppend = lineToAppend
				.replaceFirst("QUEST_ASSISTENT_GENERATED_QUESTS", quests.toLua());
		}
		return lineToAppend;
	}

	/**
	 * Replaces the briefing token with the generated quests.
	 * 
	 * @param lineToAppend Line token is in
	 * @return Lua string
	 */
	private String replaceBriefingToken(String lineToAppend)
	{
		if (lineToAppend.contains("QUEST_ASSISTENT_GENERATED_BRIEFINGS"))
		{
			lineToAppend = lineToAppend.replaceFirst("QUEST_ASSISTENT_GENERATED_BRIEFINGS", briefings.toLua());
		}
		return lineToAppend;
	}

	/**
	 *
	 * @param lineToAppend
	 * @return
	 */
	private String replaceMapSettingsToken(String lineToAppend) {
		if (lineToAppend.contains("QUEST_ASSISTENT_MAP_SETTINGS_DATA"))
		{
			lineToAppend = lineToAppend.replaceFirst("QUEST_ASSISTENT_MAP_SETTINGS_DATA", mapData.toLua());
		}
		return lineToAppend;
	}
}

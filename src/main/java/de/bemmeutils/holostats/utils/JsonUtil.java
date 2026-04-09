package de.bemmeutils.holostats.utils;

import com.google.gson.*;
import de.bemmeutils.holostats.Addon;
import de.bemmeutils.holostats.api.Hologram;
import de.bemmeutils.holostats.api.Jackpot;
import de.bemmeutils.holostats.api.Wager;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JsonUtil {
    @Getter
    private JsonObject jsonObject;

    public JsonUtil(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static JsonObject readJsonFromFile(String filePath) throws FileNotFoundException, JsonParseException {
        Scanner scanner = new Scanner(new File(filePath));
        StringBuilder jsonContent = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonContent.append(scanner.nextLine());
        }
        scanner.close();
        return new JsonParser().parse(jsonContent.toString()).getAsJsonObject();
    }

    public static JsonObject parseJson(String jsonString) throws JsonParseException {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public Jackpot getJackpot(double value) {
        if (this.jsonObject.has("jackpots")) {
            JsonArray jackpots = this.jsonObject.getAsJsonArray("jackpots");
            for (int i = 0; i < jackpots.size(); i++) {
                JsonObject jackpotObject = jackpots.get(i).getAsJsonObject();
                if (jackpotObject.has("price") && jackpotObject.get("price").getAsDouble() == value) {
                    return new Jackpot(jackpotObject.get("price").getAsInt(), jackpotObject.get("times_purchased").getAsInt(), jackpotObject.get("last_username").getAsString());
                }
            }
        }
        return null;
    }

    public List<Jackpot> getAllJackpots() {
        List<Jackpot> jackpotList = new ArrayList<>();
        if (this.jsonObject.has("jackpots")) {
            JsonArray jackpots = this.jsonObject.getAsJsonArray("jackpots");
            for (int i = 0; i < jackpots.size(); i++) {
                JsonObject jackpotObject = jackpots.get(i).getAsJsonObject();
                if (jackpotObject.has("price")) {
                    jackpotList.add(new Jackpot(
                            jackpotObject.get("price").getAsInt(),
                            jackpotObject.get("times_purchased").getAsInt(),
                            jackpotObject.get("last_username").getAsString()
                    ));
                }
            }
        }
        return jackpotList;
    }

    public void addJackpot(double value) {
        if (!this.jsonObject.has("jackpots")) {
            this.jsonObject.add("jackpots", new JsonArray());
        }
        JsonArray jackpots = this.jsonObject.getAsJsonArray("jackpots");
        JsonObject jackpotObject = new JsonObject();
        jackpotObject.addProperty("price", value);
        jackpotObject.addProperty("times_purchased", 0);
        jackpotObject.addProperty("last_username", "");
        jackpots.add(jackpotObject);
        saveJsonToFile();
    }

    public void saveJackpot(double value, int timesPurchased, String lastUsername) {
        if (!this.jsonObject.has("jackpots")) {
            this.jsonObject.add("jackpots", new JsonArray());
        }
        JsonArray jackpots = this.jsonObject.getAsJsonArray("jackpots");
        for (int i = 0; i < jackpots.size(); i++) {
            JsonObject jackpotObject = jackpots.get(i).getAsJsonObject();
            if (jackpotObject.get("price").getAsDouble() == value) {
                jackpotObject.addProperty("times_purchased", timesPurchased);
                jackpotObject.addProperty("last_username", lastUsername);
                break;
            }
        }
        saveJsonToFile();
    }

    public void saveJackpot(Jackpot jackpot) {
        if (!this.jsonObject.has("jackpots")) {
            this.jsonObject.add("jackpots", new JsonArray());
        }
        JsonArray jackpots = this.jsonObject.getAsJsonArray("jackpots");
        for (int i = 0; i < jackpots.size(); i++) {
            JsonObject jackpotObject = jackpots.get(i).getAsJsonObject();
            if (jackpotObject.get("price").getAsDouble() == jackpot.getPrice()) {
                jackpotObject.addProperty("times_purchased", jackpot.getTimesPurchased());
                jackpotObject.addProperty("last_username", jackpot.getLastUsername());
                break;
            }
        }
        saveJsonToFile();
    }

    public void removeJackpot(double value) {
        if (this.jsonObject.has("jackpots")) {
            JsonArray jackpots = jsonObject.getAsJsonArray("jackpots");
            JsonArray updatedJackpots = new JsonArray();
            for (int i = 0; i < jackpots.size(); i++) {
                JsonObject jackpotObject = jackpots.get(i).getAsJsonObject();
                if (jackpotObject.get("price").getAsInt() != value) {
                    updatedJackpots.add(jackpotObject);
                }
            }
            jsonObject.add("jackpots", updatedJackpots);
            saveJsonToFile();
        }
    }

    public Wager getWager(String uuid) {
        if (this.jsonObject.has("wagers")) {
            JsonArray wagers = this.jsonObject.getAsJsonArray("wagers");
            for (int i = 0; i < wagers.size(); i++) {
                JsonObject wagerObject = wagers.get(i).getAsJsonObject();
                if (wagerObject.has("uuid") && wagerObject.get("uuid").getAsString().equals(uuid)) {
                    return new Wager(wagerObject.get("uuid").getAsString(), wagerObject.get("player_name").getAsString(), wagerObject.get("wager").getAsDouble());
                }
            }
        }
        return null;
    }

    public void addWager(String playerUuid, String playerName, double wager) {
        if (!this.jsonObject.has("wagers")) {
            this.jsonObject.add("wagers", new JsonArray());
        }
        JsonArray wagers = this.jsonObject.getAsJsonArray("wagers");
        JsonObject wagerObject = new JsonObject();
        wagerObject.addProperty("uuid", playerUuid);
        wagerObject.addProperty("player_name", playerName);
        wagerObject.addProperty("wager", wager);
        wagers.add(wagerObject);
        saveJsonToFile();
    }

    public void saveWager(Wager wager) {
        if (!this.jsonObject.has("wagers")) {
            this.jsonObject.add("wagers", new JsonArray());
        }
        JsonArray wagers = this.jsonObject.getAsJsonArray("wagers");
        for (int i = 0; i < wagers.size(); i++) {
            JsonObject wagerObject = wagers.get(i).getAsJsonObject();
            if (wagerObject.get("uuid").getAsString().equalsIgnoreCase(wager.getUuid())) {
                wagerObject.addProperty("player_name", wager.getPlayerName());
                wagerObject.addProperty("wager", wager.getWager());
                break;
            }
        }
        saveJsonToFile();
    }

    public void removeWager(String uuid) {
        if (this.jsonObject.has("wagers")) {
            JsonArray wagers = jsonObject.getAsJsonArray("wagers");
            JsonArray updatedWagers = new JsonArray();
            for (int i = 0; i < wagers.size(); i++) {
                JsonObject wagerObject = wagers.get(i).getAsJsonObject();
                if (!wagerObject.get("uuid").getAsString().equalsIgnoreCase(uuid)) {
                    updatedWagers.add(wagerObject);
                }
            }
            jsonObject.add("wagers", updatedWagers);
            saveJsonToFile();
        }
    }

    public void addHolo(int name, Hologram.HologramType hologramType) {
        if (!this.jsonObject.has("holos")) {
            this.jsonObject.add("holos", new JsonArray());
        }
        JsonArray holos = this.jsonObject.getAsJsonArray("holos");
        JsonObject holoObject = new JsonObject();
        holoObject.addProperty("name", name);
        holoObject.addProperty("type", hologramType.name());
        holoObject.add("lines", new JsonArray());
        holos.add(holoObject);
        saveJsonToFile();
    }

    public Hologram getHologram(int name) {
        if (this.jsonObject.has("holos")) {
            JsonArray holos = this.jsonObject.getAsJsonArray("holos");
            for (int i = 0; i < holos.size(); i++) {
                JsonObject holoObject = holos.get(i).getAsJsonObject();
                if (holoObject.has("name") && holoObject.get("name").getAsInt() == name) {
                    Hologram hologram = new Hologram(
                            holoObject.get("name").getAsInt(),
                            Hologram.HologramType.valueOf(holoObject.get("type").getAsString())
                    );
                    if (holoObject.has("lines") && holoObject.get("lines").isJsonObject()) {
                        JsonObject linesObject = holoObject.getAsJsonObject("lines");
                        Map<Integer, Double> lines = new HashMap<>();
                        for (Map.Entry<String, JsonElement> entry : linesObject.entrySet()) {
                            try {
                                int lineNumber = Integer.parseInt(entry.getKey());
                                double jackpotPrice = entry.getValue().getAsDouble();
                                lines.put(lineNumber, jackpotPrice);
                            } catch (NumberFormatException e) {
                                System.err.println("Ungültige Zeilen-Nummer gefunden: " + entry.getKey());
                            }
                        }
                        hologram.setLines(lines);
                    }
                    return hologram;
                }
            }
        }
        return null;
    }

    public List<Hologram> getAllHolograms() {
        List<Hologram> hologramList = new ArrayList<>();

        if (this.jsonObject.has("holos")) {
            JsonArray holos = this.jsonObject.getAsJsonArray("holos");
            for (int i = 0; i < holos.size(); i++) {
                JsonObject holoObject = holos.get(i).getAsJsonObject();
                if (holoObject.has("name") && holoObject.has("type")) {
                    Hologram hologram = new Hologram(
                            holoObject.get("name").getAsInt(),
                            Hologram.HologramType.valueOf(holoObject.get("type").getAsString())
                    );
                    if (holoObject.has("lines") && holoObject.get("lines").isJsonObject()) {
                        JsonObject linesObject = holoObject.getAsJsonObject("lines");
                        Map<Integer, Double> lines = new HashMap<>();
                        for (Map.Entry<String, JsonElement> entry : linesObject.entrySet()) {
                            try {
                                int lineNumber = Integer.parseInt(entry.getKey());
                                double jackpotPrice = entry.getValue().getAsDouble();
                                lines.put(lineNumber, jackpotPrice);
                            } catch (NumberFormatException e) {
                                System.err.println("Ungültige Zeilen-Nummer gefunden: " + entry.getKey());
                            }
                        }
                        hologram.setLines(lines);
                    }
                    hologramList.add(hologram);
                }
            }
        }
        return hologramList;
    }

    public void removeHolo(int name) {
        if (this.jsonObject.has("holos")) {
            JsonArray holos = jsonObject.getAsJsonArray("holos");
            JsonArray updatedHolos = new JsonArray();
            for (int i = 0; i < holos.size(); i++) {
                JsonObject holoObject = holos.get(i).getAsJsonObject();
                if (holoObject.get("name").getAsInt() != name) {
                    updatedHolos.add(holoObject);
                }
            }
            jsonObject.add("holos", updatedHolos);
            saveJsonToFile();
        }
    }

    public void saveHologram(Hologram hologram) {
        if (!this.jsonObject.has("holos")) {
            this.jsonObject.add("holos", new JsonArray());
        }
        JsonArray holos = this.jsonObject.getAsJsonArray("holos");
        for (int i = 0; i < holos.size(); i++) {
            JsonObject holoObject = holos.get(i).getAsJsonObject();
            if (holoObject.get("name").getAsInt() == hologram.getName()) {
                holoObject.addProperty("type", hologram.getType().name());
                JsonObject linesObject = new JsonObject();
                for (Map.Entry<Integer, Double> entry : hologram.getLines().entrySet()) {
                    linesObject.addProperty(entry.getKey().toString(), entry.getValue());
                }
                holoObject.add("lines", linesObject);
                break;
            }
        }
        saveJsonToFile();
    }

    public void addJackpotToHolo(int name, int line, Jackpot jackpot) {
        if (line < 1 || line > 3) throw new IllegalArgumentException("Line must be between 1 and 3");

    }

    private void saveJsonToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(Addon.getADDON_FILE_PATH())) {
            gson.toJson(this.jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Wager> getTop3Wagers() {
        List<Wager> allWagers = getAllWagers();

        return allWagers.stream()
                .sorted((w1, w2) -> Double.compare(w2.getWager(), w1.getWager()))
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Wager> getAllWagers() {
        List<Wager> wagerList = new ArrayList<>();
        if (this.jsonObject.has("wagers")) {
            JsonArray wagers = this.jsonObject.getAsJsonArray("wagers");
            for (int i = 0; i < wagers.size(); i++) {
                JsonObject wagerObject = wagers.get(i).getAsJsonObject();
                if (wagerObject.has("uuid") && wagerObject.has("player_name") && wagerObject.has("wager")) {
                    Wager wager = new Wager(
                            wagerObject.get("uuid").getAsString(),
                            wagerObject.get("player_name").getAsString(),
                            wagerObject.get("wager").getAsDouble()
                    );
                    wagerList.add(wager);
                }
            }
        }
        return wagerList;
    }
}

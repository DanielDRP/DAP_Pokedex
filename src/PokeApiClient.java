import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PokeApiClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private final HttpClient client;
    private final Gson gson;

    public PokeApiClient(HttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    // Obtener los datos de todos los Pokémon, incluyendo sus estadísticas, tipos y mecánica
    public String[][] getAllPokemonData(int offset, int limit) throws Exception {
        List<String[]> allPokemonData = new ArrayList<>();

        // Obtener los nombres de todos los Pokémon
        List<String> pokemonNames = getAllPokemonNames(offset,limit);

        // Obtener los detalles de cada Pokémon
        for (String pokemonName : pokemonNames) {
            String[] pokemonData = getPokemonData(pokemonName);
            allPokemonData.add(pokemonData);
        }

        // Convertimos el List<String[]> a String[][]
        return allPokemonData.toArray(new String[0][0]);
    }

    // Obtener los nombres de todos los Pokémon (en este caso, limitados a 1000)
    private List<String> getAllPokemonNames(int offset, int limit) throws Exception {
        String url = BASE_URL + "pokemon?limit=" + limit + "&offset=" + offset;  // Cambia el límite según tus necesidades
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String> pokemonNames = new ArrayList<>();

        // Extraemos los nombres de los Pokémon
        for (int i = 0; i < results.size(); i++) {
            JsonObject pokemonObject = results.get(i).getAsJsonObject();
            String name = pokemonObject.get("name").getAsString();
            pokemonNames.add(name);
        }

        return pokemonNames;
    }

    // Obtener los datos de un solo Pokémon (nombre, sprite, stats, types, etc.)
    private String[] getPokemonData(String pokemonName) throws Exception {
        String url = BASE_URL + "pokemon/" + pokemonName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        // Obtener el nombre del Pokémon
        String name = jsonResponse.get("name").getAsString();
        // Obtener el sprite
        String sprite = jsonResponse.getAsJsonObject("sprites").get("front_default").getAsString();

        // Obtener los tipos del Pokémon
        JsonArray typesArray = jsonResponse.getAsJsonArray("types");
        StringBuilder typesBuilder = new StringBuilder();
        for (int i = 0; i < typesArray.size(); i++) {
            if (i > 0) {
                typesBuilder.append(",");
            }
            typesBuilder.append(typesArray.get(i).getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
        }

        // Obtener las estadísticas del Pokémon
        JsonArray statsArray = jsonResponse.getAsJsonArray("stats");
        String[] stats = new String[6];
        for (int i = 0; i < 6; i++) {
            stats[i] = statsArray.get(i).getAsJsonObject().get("base_stat").getAsString();
        }

        // La mecánica está fija como "Standard" para todos
        String mechanic = "Standard";

        // Devolver todos los datos como un solo String[]
        return new String[] {
                name, sprite, stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], typesBuilder.toString(), mechanic
        };
    }

}

import com.google.gson.Gson;
import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        PokeApiClient pokeApiClient = new PokeApiClient(client, gson);

        try {
            // Obtener los datos de todos los Pokémon
            String[][] allPokemonData = pokeApiClient.getAllPokemonData(493,156);

            // Imprimir los datos de todos los Pokémon
            for (String[] pokemonData : allPokemonData) {
                System.out.println("Nombre: " + pokemonData[0]);
                System.out.println("Sprite: " + pokemonData[1]);
                System.out.println("Tipos: " + pokemonData[8]);
                System.out.println("Mecánica: " + pokemonData[9]);
                System.out.println("Estadísticas (HP, Attack, Defense, Special Attack, Special Defense, Speed):");
                for (int i = 2; i < 8; i++) {
                    System.out.print(pokemonData[i] + " ");
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class PokemonImpl implements Pokemon {
    private String name;
    private String sprite;
    private int[] stats;
    private String[] types;
    private String mechanic;

    @Override
    public void parsePokemonData(String[] data) {
        if (data.length < 10) {
            throw new IllegalArgumentException("Insufficient data to parse the Pokemon.");
        }

        this.name = data[0];  // Nombre del Pokémon
        this.sprite = data[1];  // URL del sprite
        this.stats = new int[6];

        // Parseo de las estadísticas (los primeros 6 elementos después del nombre y sprite)
        for (int i = 0; i < 6; i++) {
            this.stats[i] = Integer.parseInt(data[i + 2]);  // Parse stats
        }

        // Parseo de los tipos (en el índice 8, separados por coma)
        this.types = data[8].split(",");

        // La mecánica está en el índice 9
        this.mechanic = data[9];
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSprite() {
        return sprite;
    }

    @Override
    public int[] getStats() {
        return stats;
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getMechanic() {
        return mechanic;
    }
}

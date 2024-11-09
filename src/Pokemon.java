public interface Pokemon {
    void parsePokemonData(String[] data);
    String getName();
    String getSprite();
    int[] getStats();// hp, attack, defense, special attack, special defense, speed
    String[] getTypes();
    String getMechanic();
}

import java.util.ArrayList;
import java.util.List;

public class Ingredients{

    public final List<String> ingredients;

    public Ingredients(List<String> ingredients){
       this.ingredients=ingredients;
    }
    public static Ingredients getIngredients(List<String> ingredients){
       return new Ingredients(ingredients);
    }
}

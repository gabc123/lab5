
package GameData;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author mats
 */
public class GraphicModels {
    
    private ArrayList<Image> graphicModels;
    
    public GraphicModels() {
        graphicModels = new ArrayList<Image>();
    }
    
    public void loadmodel(String[] modelNames){
        for(String name: modelNames) {
            graphicModels.add(new Image(name));
        }
    }
    
    public Image getModel(int modelID, int state){
        return graphicModels.get(modelID);
    }
}

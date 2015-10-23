
package GameData;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * Stores all image/model data used by the rendering
 * also can preform animations, if added =)
 * @author mats
 */
public class GraphicModels {
    
    private ArrayList<Image> graphicModels;
    /**
     * Constructor
     */
    public GraphicModels() {
        graphicModels = new ArrayList<Image>();
    }
    
    /**
     * Loads all modelNames into this class, for later use
     * @param modelNames String array of all models
     */
    public void loadmodel(String[] modelNames){
        for(String name: modelNames) {
            graphicModels.add(new Image(name));
        }
    }
    
    /**
     * Gets the image for with the modelId, and state
     * @param modelID what model to use
     * @param state can later be used to select key frames for a model
     * @return 
     */
    public Image getModel(int modelID, int state){
        return graphicModels.get(modelID);
    }
}

package dev.tobynguyen27.codebebelib.render.pipeline.attribute;

import dev.tobynguyen27.codebebelib.colour.ColourRGBA;
import dev.tobynguyen27.codebebelib.render.CCRenderState;
import dev.tobynguyen27.codebebelib.render.pipeline.VertexAttribute;

/**
 * Sets colour in CCRS to the specified colour in the model.
 */
public class ColourAttribute extends VertexAttribute<int[]> {

    public static final AttributeKey<int[]> attributeKey = AttributeKey.create("colour", int[]::new);

    private int[] colourRef;

    public ColourAttribute() {
        super(attributeKey);
    }

    @Override
    public boolean load(CCRenderState ccrs) {
        colourRef = ccrs.model.getAttribute(attributeKey);
        return colourRef != null || !ccrs.model.hasAttribute(attributeKey);
    }

    @Override
    public void operate(CCRenderState ccrs) {
        if (colourRef != null) {
            ccrs.colour = ColourRGBA.multiply(ccrs.baseColour, colourRef[ccrs.vertexIndex]);
        } else {
            ccrs.colour = ccrs.baseColour;
        }
    }
}

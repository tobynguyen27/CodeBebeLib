package dev.tobynguyen27.codebebelib.model;

import com.mojang.math.Transformation;
import dev.tobynguyen27.codebebelib.utils.ArrayUtils;
import dev.tobynguyen27.codebebelib.utils.LambdaUtils;
import io.github.fabricators_of_create.porting_lib.model.ItemLayerModel;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * Created by covers1624 on 13/02/2017.
 */
public class ItemQuadBakery {

    public static List<BakedQuad> bakeItem(TextureAtlasSprite... sprites) {
        return bakeItem(Transformation.identity(), sprites);
    }

    public static List<BakedQuad> bakeItem(Transformation transform, TextureAtlasSprite... sprites) {

        LambdaUtils.checkArgument(sprites, "Sprites must not be Null or empty!", ArrayUtils::isNullOrContainsNull);

        List<BakedQuad> quads = new LinkedList<>();
        for (int i = 0; i < sprites.length; i++) {
            TextureAtlasSprite sprite = sprites[i];
            quads.addAll(ItemLayerModel.getQuadsForSprite(i, sprite, transform));
        }
        return quads;
    }
}

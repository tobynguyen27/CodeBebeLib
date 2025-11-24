package dev.tobynguyen27.codebebelib.model.pipeline;

import io.github.fabricators_of_create.porting_lib.model.IVertexConsumer;

public interface IVertexProducer {
    /**
     * @param consumer consumer to receive the vertex data this producer can provide
     */
    void pipe(IVertexConsumer consumer);
}

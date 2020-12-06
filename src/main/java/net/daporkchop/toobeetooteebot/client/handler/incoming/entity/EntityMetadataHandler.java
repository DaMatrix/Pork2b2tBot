/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.client.handler.incoming.entity;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import lombok.NonNull;
import net.daporkchop.toobeetooteebot.client.PorkClientSession;
import net.daporkchop.toobeetooteebot.util.cache.data.entity.Entity;
import net.daporkchop.toobeetooteebot.util.handler.HandlerRegistry;

import java.util.ArrayList;

import static net.daporkchop.toobeetooteebot.util.Constants.*;

/**
 * @author DaPorkchop_
 */
public class EntityMetadataHandler implements HandlerRegistry.IncomingHandler<ServerEntityMetadataPacket, PorkClientSession> {
    @Override
    public boolean apply(@NonNull ServerEntityMetadataPacket packet, @NonNull PorkClientSession session) {
        Entity entity = CACHE.getEntityCache().get(packet.getEntityId());
        if (entity != null) {
            MAINLOOP:
            for (EntityMetadata metadata : packet.getMetadata())    {
                for (int i = entity.getMetadata().size() - 1; i >= 0; i--)  {
                    EntityMetadata old = entity.getMetadata().get(i);
                    if (old.getId() == metadata.getId())    {
                        entity.getMetadata().set(i, metadata);
                        continue MAINLOOP;
                    }
                }

                if(entity.getMetadata() instanceof ArrayList) {
                    entity.getMetadata().add(metadata);
                } else if(CONFIG.log.sendWarning) {
                    // TODO make that info less
                    CLIENT_LOG.warn("Received ServerEntityMetadataPacket for an entity that cant accept those (probably happened because you are on a 1.16 server. You can't see the sneaking of other players now eg)");

                }

            }
        } else if(CONFIG.log.sendWarning) {
            CLIENT_LOG.warn("Received ServerEntityMetadataPacket for invalid entity (id=%d)", packet.getEntityId());
        }
        return true;
    }

    @Override
    public Class<ServerEntityMetadataPacket> getPacketClass() {
        return ServerEntityMetadataPacket.class;
    }
}

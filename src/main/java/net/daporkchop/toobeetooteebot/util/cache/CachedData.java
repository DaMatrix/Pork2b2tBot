/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2018 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.util.cache;

import com.github.steveice10.packetlib.packet.Packet;
import lombok.NonNull;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author DaPorkchop_
 */
public interface CachedData {
    ThreadLocal<Collection<Packet>> ZZZ_collection_cache = ThreadLocal.withInitial(ArrayDeque::new);

    default Collection<Packet> getPackets() {
        Collection<Packet> collection = ZZZ_collection_cache.get();
        collection.clear();
        this.getPacketsSimple(collection::add);
        return collection;
    }

    void getPacketsSimple(@NonNull Consumer<Packet> consumer);

    void reset();

    default String getSendingMessage()  {
        return null;
    }
}
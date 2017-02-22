package com.cosmoport.core.api;

import com.cosmoport.core.node.NodesHolder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/nodes")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class NodesEndpoint {
    private final NodesHolder nodesHolder;

    @Inject
    public NodesEndpoint(NodesHolder nodesHolder) {
        this.nodesHolder = nodesHolder;
    }

    @GET
    @Path("/")
    public Map<String, Byte> get() {
        Map<String, Byte> nodes = new HashMap<>();
        nodes.put("timetables", (byte) nodesHolder.getTables());
        nodes.put("gates", (byte) nodesHolder.getGates());

        return nodes;
    }
}

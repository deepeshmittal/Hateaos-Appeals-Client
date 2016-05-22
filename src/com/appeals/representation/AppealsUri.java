package com.appeals.representation;

import java.net.URI;
import java.net.URISyntaxException;

import com.appeals.object.Identifier;

public class AppealsUri {
    private URI uri;
    
    public AppealsUri(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealsUri(URI uri) {
        this(uri.toString());
    }

    public AppealsUri(URI uri, Identifier identifier) {
        this(uri.toString() + "/" + identifier.toString());
    }

    public Identifier getId() {
        String path = uri.getPath();
        return new Identifier(path.substring(path.lastIndexOf("/") + 1, path.length()));
    }

    public URI getFullUri() {
        return uri;
    }
    
    public String toString() {
        return uri.toString();
    }
    
    public boolean equals(Object obj) {
        if(obj instanceof AppealsUri) {
            return ((AppealsUri)obj).uri.equals(uri);
        }
        return false;
    }

    public String getBaseUri() {
        String uriString = uri.toString();
        String baseURI   = uriString.substring(0, uriString.lastIndexOf("webresources/")+"webresources".length());
        
        return baseURI;
    }
}

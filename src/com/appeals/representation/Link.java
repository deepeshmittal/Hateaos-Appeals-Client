package com.appeals.representation;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = Representation.DAP_NAMESPACE)
public class Link {
    
    
    @XmlAttribute(name = "rel")
    private String rel;
    @XmlAttribute(name = "uri")
    private String uri;

    @XmlAttribute(name = "mediaType")
    private String mediaType;

    /**
     * For JAXB :-(
     */
    Link() {
    }

    public Link(String name, AppealsUri uri, String mediaType) {
        
        this.rel = name;
        this.uri = uri.getFullUri().toString();
        this.mediaType = mediaType;

    }

    public Link(String name, AppealsUri uri) {
        this(name, uri, Representation.CSE564APPEALS_MEDIA_TYPE);
    }

    public String getRelValue() {
        return rel;
    }

    public URI getUri() {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMediaType() {
        return mediaType;
    }
}

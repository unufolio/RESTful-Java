package io.unufolio.restful;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.*;
import org.springframework.lang.Nullable;

/**
 * @author Unufolio unufolio@gmail.com
 */
public class HateoasLink extends Link {

    private LinkRelation rel;
    private String href;
    @Nullable
    private String hreflang;
    @Nullable
    private String media;
    @Nullable
    private String title;
    @Nullable
    private String type;
    @Nullable
    private String deprecation;
    @Nullable
    private String profile;
    @Nullable
    private String name;
    @Nullable
    private String method;

    private HateoasLink(Link link) {
        super();
        this.rel = link.getRel();
        this.href = link.getHref();
        this.hreflang = link.getHreflang();
        this.media = link.getMedia();
        this.title = link.getTitle();
        this.type = link.getType();
        this.deprecation = link.getDeprecation();
        this.profile = link.getProfile();
        this.name = link.getName();
    }

    public static HateoasLink of(Link link) {
        return new HateoasLink(link);
    }

    public HateoasLink withMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    @JsonProperty
    public LinkRelation getRel() {
        return this.rel;
    }

    @Override
    @JsonProperty
    public String getHref() {
        return this.href;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getHreflang() {
        return this.hreflang;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getMedia() {
        return this.media;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getTitle() {
        return this.title;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getType() {
        return this.type;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getDeprecation() {
        return this.deprecation;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getProfile() {
        return this.profile;
    }

    @Override
    @Nullable
    @JsonProperty
    public String getName() {
        return this.name;
    }

    public String getMethod() {
        return method;
    }
}

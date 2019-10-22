package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"css", "attributes", "id", "classes", "tag", "nthChild"})*/
public class ResponsePropertyData {

/*    @JsonProperty("css")
    private List<String> css = null;

    @JsonProperty("attributes")
    private List<String> attributes = null;

    @JsonProperty("classes")
    private List<String> classes = null;

    @JsonProperty("id")
    private String id;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("nthChild")
    private String nthChild;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("css")
    public List<String> getCss() {
        return css;
    }

    @JsonProperty("css")
    public void setCss(List<String> css) {
        this.css = css;
    }

    @JsonProperty("attributes")
    public List<String> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("classes")
    public List<String> getClasses() {
        return classes;
    }

    @JsonProperty("classes")
    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    @JsonProperty("nthChild")
    public String getNthChild() {
        return nthChild;
    }

    @JsonProperty("nthChild")
    public void setNthChild(String nthChild) {
        this.nthChild = nthChild;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }*/

}
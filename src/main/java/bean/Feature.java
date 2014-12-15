package bean;

/**
 * User : Emmanuel PIERRE
 * Date: 30/05/14
 * Time: 14:31
 */
public class Feature {
    String featureClass;
    String featureCode;

    public Feature(String featureClass, String featureCode) {
        this.featureClass = featureClass;
        this.featureCode = featureCode;
    }

    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
}

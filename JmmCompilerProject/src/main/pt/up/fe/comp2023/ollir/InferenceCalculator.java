package pt.up.fe.comp2023.ollir;

public class InferenceCalculator {
    private final String inferredType;
    private final Boolean assignToTemp;
    private String scope;
    private Boolean haveAssign;

    public InferenceCalculator(String inferredType, Boolean assignToTemp, String scope) {
        this.inferredType = inferredType;
        this.assignToTemp = assignToTemp;
        this.scope = scope;
        this.haveAssign = false;
    }

    public InferenceCalculator(Boolean assignToTemp, String scope) {
        this(null, assignToTemp, scope);
    }

    public String getInferredType() {
        return inferredType;
    }

    public boolean getIsToAssignToTemp() {
        return assignToTemp;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Boolean getHaveAssign() {
        return haveAssign;
    }

    public void setHaveAssign(Boolean haveAssign) {
        this.haveAssign = haveAssign;
    }
}

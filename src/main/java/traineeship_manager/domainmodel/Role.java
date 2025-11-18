package traineeship_manager.domainmodel;

public enum Role {
    STUDENT("Student"),
    COMPANY("Company"),
    PROFESSOR("Professor"),
    ADMIN("Committee");

    private final String value;
    private Role(String value) { this.value = value; }
    public String getValue() { return value; }
}

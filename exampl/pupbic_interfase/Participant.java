package pupbic_interfase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "name",
        "family",
        "organization",
        "report",
        "email"
})
public class Participant {
    private String name;
    private String family;
    private String organization;
    private String report;
    private String email;

    public Participant(String name, String family, String organization, String report, String email) {
        this.name = name;
        this.family = family;
        this.organization = organization;
        this.report = report;
        this.email = email;
    }

    public Participant() { }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getOrganization() {
        return organization;
    }

    public String getReport() {
        return report;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return name.equals(that.name) && family.equals(that.family) && organization.equals(that.organization) && report.equals(that.report) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, family, organization, report, email);
    }

    @Override
    public String toString() {
        return  "name = " + name + '\n' +
                "family = " + family + '\n' +
                "organization = " + organization + '\n' +
                "report = " + report + '\n' +
                "email = " + email + '\n';
    }
}

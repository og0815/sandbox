package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property", uniqueConstraints = @UniqueConstraint(columnNames = "PK_IDObject"))
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PK_IDObject", unique = true)
    private Long PK_IDObject;

    private String appelation;
    private String objectGroup;
    private String street;
    private Integer zip;
    private String city;
    private Double latitude;
    private Double longitude;
    private String line;
    private String lineSection;
    private Integer driveTimeToCentralStation;
    private Integer capacityMax;
    private Integer capacityHandicapped;
    private Integer capacityRent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPK_IDObject() { return PK_IDObject; }
    public void setPK_IDObject(Long PK_IDObject) { this.PK_IDObject = PK_IDObject; }

    public String getAppelation() { return appelation; }
    public void setAppelation(String appelation) { this.appelation = appelation; }

    public String getObjectGroup() { return objectGroup; }
    public void setObjectGroup(String objectGroup) { this.objectGroup = objectGroup; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public Integer getZip() { return zip; }
    public void setZip(Integer zip) { this.zip = zip; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }

    public String getLineSection() { return lineSection; }
    public void setLineSection(String lineSection) { this.lineSection = lineSection; }

    public Integer getDriveTimeToCentralStation() { return driveTimeToCentralStation; }
    public void setDriveTimeToCentralStation(Integer driveTimeToCentralStation) { this.driveTimeToCentralStation = driveTimeToCentralStation; }

    public Integer getCapacityMax() { return capacityMax; }
    public void setCapacityMax(Integer capacityMax) { this.capacityMax = capacityMax; }

    public Integer getCapacityHandicapped() { return capacityHandicapped; }
    public void setCapacityHandicapped(Integer capacityHandicapped) { this.capacityHandicapped = capacityHandicapped; }

    public Integer getCapacityRent() { return capacityRent; }
    public void setCapacityRent(Integer capacityRent) { this.capacityRent = capacityRent; }
}

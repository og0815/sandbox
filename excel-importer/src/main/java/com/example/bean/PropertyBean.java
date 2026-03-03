package com.example.bean;

import java.io.Serializable;
import java.util.List;

import com.example.entity.Property;
import com.example.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class PropertyBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(PropertyBean.class);

    @Inject
    private ImportService importService;

    private List<Property> properties;

    @PostConstruct
    public void init() {
        log.info("Initializing PropertyBean - loading all properties from database");
        properties = importService.getAllProperties();
        log.info("Loaded {} properties from database", properties.size());
    }

    public List<Property> getProperties() {
        return properties;
    }
}

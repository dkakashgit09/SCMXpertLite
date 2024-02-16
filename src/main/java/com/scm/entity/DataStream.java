package com.scm.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "datastream")
public class DataStream 
{
    private int Device_ID;
    private double Battery_Level; 
    private double First_Sensor_temperature; 
    private String Route_From; 
    private String Route_To;
    private String Time_Stamp;
}

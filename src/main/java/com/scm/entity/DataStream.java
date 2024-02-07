package com.scm.entity;

import lombok.Data;

@Data
public class DataStream 
{
    private int Device_ID;
    private double Battery_Level; 
    private double First_Sensor_temperature; 
    private String Route_From; 
    private String Route_To;
    private String TimeStamp;
}

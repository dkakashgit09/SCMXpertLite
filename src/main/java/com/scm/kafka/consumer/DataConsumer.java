package com.scm.kafka.consumer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.scm.entity.DataStream;
import com.scm.repo.DataStreamRepository;

@Component
@PropertySource("application.properties")
public class DataConsumer 
{
	private final DataStreamRepository dataStreamRepo;

//	@Autowired
	public DataConsumer(DataStreamRepository dataStreamRepo) 
	{
		this.dataStreamRepo = dataStreamRepo;
	}
    
    @KafkaListener(topics = "streamdata", groupId = "group_id")
    public void receive(String message) 
    {
        System.out.println("Received message: " + message);
        try 
        {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) 
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DataStream dataStream = new DataStream();
                dataStream.setBattery_Level(jsonObject.getDouble("Battery_Level"));
                dataStream.setDevice_ID(jsonObject.getInt("Device_Id"));
                dataStream.setFirst_Sensor_temperature(jsonObject.getDouble("First_Sensor_temperature"));
                dataStream.setRoute_From(jsonObject.getString("Route_From"));
                dataStream.setRoute_To(jsonObject.getString("Route_To"));
                // Get current timestamp in milliseconds
                long timestampMillis = System.currentTimeMillis();

                // Format timestamp to human-readable string
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z");
                dateFormatter.setTimeZone(TimeZone.getTimeZone("IST"));
                String timestampString = dateFormatter.format(new Date(timestampMillis));

                // Set Time_Stamp with received timestamp
                dataStream.setTime_Stamp(timestampString);
                dataStreamRepo.save(dataStream);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Error processing JSON array: " + e.getMessage());
        }
    }
}

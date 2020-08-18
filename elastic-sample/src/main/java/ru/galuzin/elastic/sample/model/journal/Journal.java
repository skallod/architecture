package ru.galuzin.elastic.sample.model.journal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

@Document(indexName = "app_journal", type = "journal")
public class Journal
{
    @Id
    private String id;

    //unique 1
    @Field(type = Keyword)
    private String serviceId;

    //unique 2
    //date in elastic
    @Field(type = FieldType.Date)
    private Date partitionDate;

    //unique 3
    //
    @Field(type = Keyword)
    private String serviceIdType;

    @Field(type = FieldType.Date)
    private Date eventDate;

    public Journal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getPartitionDate() {
        return partitionDate;
    }

    public void setPartitionDate(Date partitionDate) {
        this.partitionDate = partitionDate;
    }

    public String getServiceIdType() {
        return serviceIdType;
    }

    public void setServiceIdType(String serviceIdType) {
        this.serviceIdType = serviceIdType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}

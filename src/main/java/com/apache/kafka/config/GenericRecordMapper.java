package com.apache.kafka.config;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.reflect.ReflectData;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

public class GenericRecordMapper {

    public static GenericData.Record mapObjectToRecord(Object object) {
        Assert.notNull(object, "object must not be null");
        final Schema schema = ReflectData.get().getSchema(object.getClass());
        final GenericData.Record record = new GenericData.Record(schema);
        schema.getFields().forEach(r -> record.put(r.name(), PropertyAccessorFactory.forDirectFieldAccess(object).getPropertyValue(r.name())));
        System.out.println("record="+record);
        return record;
    }

    public static <T> T mapRecordToObject(GenericData.Record record, T object) {
        Assert.notNull(record, "record must not be null");
        Assert.notNull(object, "object must not be null");

        final Schema schema = ReflectData.get().getSchema(object.getClass());
        Assert.isTrue(schema.getFields().equals(record.getSchema().getFields()), "Schema fields didn't match");

        record
                .getSchema()
                .getFields()
                .forEach(field ->
                    PropertyAccessorFactory
                            .forDirectFieldAccess(object)
                            .setPropertyValue(field.name(), record.get(field.name()))
                );
        return object;
    }
}
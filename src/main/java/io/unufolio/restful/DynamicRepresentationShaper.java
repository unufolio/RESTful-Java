package io.unufolio.restful;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Unufolio unufolio@gmail.com
 */
public class DynamicRepresentationShaper {

    private final List<String> fields;

    public DynamicRepresentationShaper(List<String> fields) {
        this.fields = fields;
    }

    public DynamicRepresentation toModel(Thing entity) {
        DynamicRepresentation representation = new DynamicRepresentation();
        if (fields == null || fields.isEmpty()) {
            try {
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    representation.put(field.getName(), field.get(entity));
                }
                return representation;
            } catch (IllegalAccessException e) {
                // 处理字段无法访问的情况
            }
        } else {
            for (String fieldName : fields) {
                try {
                    Field field = Thing.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    representation.put(fieldName, field.get(entity));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // 处理字段不存在或无法访问的情况
                }
            }
        }
        return representation;
    }
}

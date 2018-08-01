/*
 *
 * This file is generated under this project, "open-commons-tool-json".
 *
 * Date  : 2014. 10. 29. 오후 12:28:14
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tool.json.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import open.commons.config.ReferenceableProperties;
import open.commons.tool.json.JavaClassTemplate;

public class JavaClassTemplateProvider {

    private static Map<String, JavaClassTemplate> templates = new HashMap<>();

    static {
        Properties properties = null;
        try {
            properties = new ReferenceableProperties();
            properties.load(JavaClassTemplate.class.getResourceAsStream("/resource/java-class-template.properties"));

            JavaClassTemplate tpl = null;
            String[] name = null;
            String value = null;
            for (Entry<Object, Object> entry : properties.entrySet()) {
                name = ((String) entry.getKey()).split("\\.");
                value = (String) entry.getValue();

                tpl = templates.get(name[0]);
                if (tpl == null) {
                    tpl = new JavaClassTemplate(name[0]);
                    templates.put(tpl.getCategory(), tpl);
                }

                tpl.set(name[1], value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static String[] getCategories() {
        return templates.keySet().toArray(new String[0]);
    }

    public static JavaClassTemplate getTemplate(String category) {
        return templates.get(category);
    }
}

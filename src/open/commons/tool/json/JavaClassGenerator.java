/*
 *
 * This file is generated under this project, "iCanvas".
 *
 * Date  : 2014. 10. 24. 오전 9:55:10
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tool.json;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import open.commons.collection.FIFOMap;
import open.commons.text.NamedTemplate;
import open.commons.tool.json.JavaClassTemplate.ArrayHandler;
import open.commons.tool.json.template.JavaClassTemplateProvider;

import code.org.codehaus.jettison.json.JSONArray;
import code.org.codehaus.jettison.json.JSONException;
import code.org.codehaus.jettison.json.JSONObject;

public class JavaClassGenerator {

    static final NamedTemplate TPL_CLASS = new NamedTemplate("public class {class} \\{" //
            + "\n\t{fields}" //
            + "\n\tpublic {class} \\{\\}"//
            + "\n\t{methods}"//
            + "\n\\}");

    static final NamedTemplate TPL_FIELD = new NamedTemplate("private {type} {var};");
    static final NamedTemplate TPL_FIELD_WITH_VALUE = new NamedTemplate("private {type} {var} = {value};");
    static final NamedTemplate TPL_SETTER_METHOD = new NamedTemplate("public void set{name} ({type} {var}) \\{\n\tthis.{var} = {var};\n\\}");
    static final NamedTemplate TPL_GETTER_METHOD = new NamedTemplate("public {type} get{name} () \\{\n\treturn this.{var};\n\\}");

    static Set<String> nestedClasses = new HashSet<>();

    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd.");

    static JavaClassTemplate template;

    private static void addNullTypeImport(JavaClassAttrContainer jcaContainer) {
        Set<String> nullTypeImports = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE_IMPORT);
        for (String nullTypeImport : nullTypeImports) {
            jcaContainer.addImport(template.createImport(nullTypeImport));
        }
    }

    public static FIFOMap<String, JavaClassAttrContainer> convert(String className, String jsonStr, String category, boolean childrenIsNested) throws UnsupportedEncodingException,
            JSONException, FileNotFoundException {

        JavaClassAttrContainer.flush();

        template = JavaClassTemplateProvider.getTemplate(category);

        String tmp = jsonStr.trim();
        if (tmp.startsWith("{")) {
            JavaClassAttrContainer.applyClassAttrConfigs(className, createJavaClass(className, new JSONObject(jsonStr), null), template);
        } else if (tmp.startsWith("[")) {
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
                JavaClassAttrContainer.applyClassAttrConfigs(className, createJavaClass(className, jsonArray.getJSONObject(i), null), template);
            }
        }

        return JavaClassAttrContainer.evaluate(childrenIsNested);
    }

    @SuppressWarnings("unchecked")
    static Map<String, ClassAttrConfig> createJavaClass(String className, JSONObject jsonObj, String parentClassName) throws JSONException {

        JavaClassAttrContainer jcaContainer = new JavaClassAttrContainer(template);
        JavaClassAttrContainer.addClassJsonSource(className, jsonObj);
        jcaContainer.setClass(className);
        jcaContainer.setParentClass(parentClassName);

        Iterator<String> itrKeys = (Iterator<String>) jsonObj.keys();

        Map<String, ClassAttrConfig> fgsMap = new HashMap<>();
        String key = null;
        Class<?> valueType = null;
        Object value = null;

        ClassAttrConfig fgs = null;
        while (itrKeys.hasNext()) {
            key = itrKeys.next();
            value = jsonObj.get(key);
            valueType = value.getClass();

            fgs = resolve(key, value, valueType, jcaContainer);

            if (fgs == null) {
                continue;
            }

            fgsMap.put(key, fgs);
        }

        JavaClassAttrContainer.addJavaClassAttrContainer(className, jcaContainer);

        return fgsMap;
    }

    static ClassAttrConfig resolve(String key, Object value, Class<?> valueType, JavaClassAttrContainer jcaContainer) throws JSONException {

        String date = sdf.format(Calendar.getInstance().getTime());

        ClassAttrConfig fgs = new ClassAttrConfig();

        if (JSONObject.class.equals(valueType)) {
            fgs.field = template.createField(key, JavaClassTemplate.toUpperCamelCase(key), key);
            fgs.setter = template.createSetter(JavaClassTemplate.toUpperCamelCase(key), key, key, date, jcaContainer.getClassName());
            fgs.getter = template.createGetter(JavaClassTemplate.toUpperCamelCase(key), key, key, date, jcaContainer.getClassName());

            JavaClassAttrContainer.applyClassAttrConfigs(JavaClassTemplate.toUpperCamelCase(key),
                    createJavaClass(JavaClassTemplate.toUpperCamelCase(key), (JSONObject) value, jcaContainer.getClassName()), template);

        } else {

            String type = null;
            String defaultValue = null;

            if (JSONArray.class.equals(valueType)) {
                JSONArray jsonArray = (JSONArray) value;

                if (template.getTplImport() != null) {
                    jcaContainer.addImport(template.createImport("java.util.List"));
                    jcaContainer.addImport(template.createImport("java.util.ArrayList"));
                }

                String arrayType = null;
                if (jsonArray.length() < 1) {
                    addNullTypeImport(jcaContainer);

                    arrayType = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE);
                } else {
                    Class<?> elemType = jsonArray.get(0).getClass();

                    if (JSONObject.class.equals(elemType)) {

                        arrayType = JavaClassTemplate.toUpperCamelCase(key);

                        JSONObject elemJsonObj = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            elemJsonObj = jsonArray.getJSONObject(i);
                            JavaClassAttrContainer.applyClassAttrConfigs(JavaClassTemplate.toUpperCamelCase(key),
                                    createJavaClass(JavaClassTemplate.toUpperCamelCase(key), (JSONObject) elemJsonObj, jcaContainer.getClassName()), template);
                        }

                    } else
                    // 다중 배열은 처리 안함.
                    if (JSONArray.class.equals(elemType)) {
                        arrayType = "?";
                    } else if (JSONObject.NULL.getClass().equals(elemType)) {
                        addNullTypeImport(jcaContainer);

                        arrayType = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE);
                    } else {
                        arrayType = elemType.getSimpleName();
                    }
                }

                ArrayHandler arrayHandler = new ArrayHandler();
                arrayHandler.mature((String) template.getAttribute(JavaClassTemplate.ATTR_ARRAY_HANDLER));
                
                type = new NamedTemplate(arrayHandler.getType()).addValue("elemType", arrayType).format();
                defaultValue = arrayHandler.getImplClass();
                
                fgs.field = template.createFieldWidthValue(key, type, key, defaultValue);
                fgs.setter = template.createSetter(type, key, key, date, jcaContainer.getClassName());
                fgs.getter = template.createGetter(type, key, key, date, jcaContainer.getClassName());


            } else if (Integer.class.equals(valueType)) {
                type = "Integer";
                defaultValue = "new Integer(0)";

            } else if (Long.class.equals(valueType)) {
                type = "Long";
                defaultValue = "new Long(0)";

            } else if (Double.class.equals(valueType)) {
                type = "Double";
                defaultValue = "new Double(0.0)";

            } else if (String.class.equals(valueType)) {
                type = "String";

            } else if (Boolean.class.equals(valueType)) {
                type = "Boolean";
                defaultValue = "new Boolean(false)";

            } else if (JSONObject.NULL.getClass().equals(valueType)) {
                addNullTypeImport(jcaContainer);

                type = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE);
                defaultValue = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE_VALUE);
            }

            if (type == null) {
                return null;
            }

            fgs.key = key;
            fgs.type = type;
            fgs.field = defaultValue == null ? template.createField(key, type, key) : template.createFieldWidthValue(key, type, key, defaultValue);
            fgs.setter = template.createSetter(type, key, key, date, jcaContainer.getClassName());
            fgs.getter = template.createGetter(type, key, key, date, jcaContainer.getClassName());
        }

        return fgs;
    }
}

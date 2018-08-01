/*
 *
 * This file is generated under this project, "iCanvas".
 *
 * Date  : 2014. 10. 24. 오전 10:54:51
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tool.json;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

import open.commons.json.annotation.JSONField;
import open.commons.json.model.DefaultJSONModel;
import open.commons.json.model.JSONArrayFactory;
import open.commons.text.NamedTemplate;
import open.commons.utils.ArrayUtils;
import open.commons.utils.StringUtils;

public class JavaClassTemplate {

    public static final String ELEM_CLASS = "class";
    public static final String ELEM_IMPORT = "import";
    public static final String ELEM_FIELD = "field";
    public static final String ELEM_FIELD_WITH_VALUE = "field_with_value";
    public static final String ELEM_SETTER = "setter";
    public static final String ELEM_GETTER = "getter";

    public static final String ATTR_FIELD_IMPORT = "fieldImport";
    public static final String ATTR_OBJECT_IMPORT = "objectImport";
    public static final String ATTR_NULL_TYPE = "nullType";
    public static final String ATTR_NULL_TYPE_VALUE = "nullTypeValue";
    public static final String ATTR_NULL_TYPE_IMPORT = "nullTypeImport";
    public static final String ATTR_SERIALIZABLE = "serializable";
    public static final String ATTR_EXTENSION = "extension";
    public static final String ATTR_ARRAY_HANDLER = "arrayhandler";

    private static final Collection<String> JavaLanguageKeywords = new HashSet<>();

    static {
        String[] keywords = { "abstract", "assert" //
                , "boolean", "break", "byte" //
                , "case", "catch", "char", "class", "const", "continue" //
                , "default", "do", "double" //
                , "else", "enum", "extends" //
                , "final", "finally", "float", "for" //
                , "goto" //
                , "if", "implements", "import", "instanceof", "int", "interface" //
                , "long" //
                , "native", "new", "null" // 'null' is value keyword.
                , "package", "private", "protected", "public" //
                , "return" //
                , "short", "static", "strictfp", "super", "switch", "synchronized" //
                , "this", "throw", "throws", "transient", "try" //
                , "void", "volatile" //
                , "while"

        };

        JavaLanguageKeywords.addAll(Arrays.asList(keywords));
    }

    private Collection<String> elementList = new HashSet<>();
    private Collection<String> attributeList = new HashSet<>();

    private Map<String, Object> attributes = new HashMap<>();

    {
        // start - set elements : 2014. 11. 3. 오전 9:48:29
        elementList.add(ELEM_CLASS);
        elementList.add(ELEM_IMPORT);
        elementList.add(ELEM_FIELD);
        elementList.add(ELEM_FIELD_WITH_VALUE);
        elementList.add(ELEM_SETTER);
        elementList.add(ELEM_GETTER);
        // end - set elements : 2014. 11. 3. 오전 9:48:29

        // start - set attributes : 2014. 11. 3. 오전 9:48:36
        attributeList.add(ATTR_FIELD_IMPORT);
        attributeList.add(ATTR_OBJECT_IMPORT);
        attributeList.add(ATTR_NULL_TYPE);
        attributeList.add(ATTR_NULL_TYPE_VALUE);
        attributeList.add(ATTR_NULL_TYPE_IMPORT);
        attributeList.add(ATTR_SERIALIZABLE);
        attributeList.add(ATTR_EXTENSION);
        attributeList.add(ATTR_ARRAY_HANDLER);

        attributes.put(ATTR_FIELD_IMPORT, new ConcurrentSkipListSet<String>());
        attributes.put(ATTR_OBJECT_IMPORT, new ConcurrentSkipListSet<String>());
        attributes.put(ATTR_NULL_TYPE_IMPORT, new ConcurrentSkipListSet<String>());
        // end - set attributes : 2014. 11. 3. 오전 9:48:36

    }

    private String category;

    private String tplClass;
    private String tplImport;
    private String tplField;
    private String tplFieldWithValue;
    private String tplSetter;
    private String tplGetter;

    public JavaClassTemplate(String category) {
        this.category = category;
    }

    public String createField(String jsonKey, String type, String var) {
        return tplField()//
                .addValue("jsonKey", jsonKey)//
                .addValue("type", type)//
                .addValue("var", toJavaField(toLowerCamelCase(var)))//
                .format();
    }

    public String createFieldWidthValue(String jsonKey, String type, String var, String value) {
        return tplFieldWithValue()//
                .addValue("jsonKey", jsonKey)//
                .addValue("type", type)//
                .addValue("var", toJavaField(toLowerCamelCase(var)))//
                .addValue("value", value)//
                .format();
    }

    public String createGetter(String type, String name, String var, String date, String object) {
        return createMethod(tplGetter(), type, toUpperCamelCase(name), toJavaField(toLowerCamelCase(var)), date, name, object);
    }

    public String createImport(String qualifier) {
        return tplImport().addValue("qualifier", qualifier).format();
    }

    private String createMethod(NamedTemplate tpl, String type, String name, String var, String date, String jsonKey, String object) {
        return tpl//
                .addValue("type", type)//
                .addValue("name", name)//
                .addValue("var", var)//
                .addValue("date", date)//
                .addValue("jsonKey", jsonKey) // for 'javascript'
                .addValue("object", object) // for 'javascript'
                .format();
    }

    public String createSetter(String type, String name, String var, String date, String object) {
        return createMethod(tplSetter(), type, toUpperCamelCase(name), toJavaField(toLowerCamelCase(var)), date, name, object);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaClassTemplate other = (JavaClassTemplate) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attr) {
        return (T) attributes.get(attr);
    }

    public String getCategory() {
        return category;
    }

    public String getTplClass() {
        return tplClass;
    }

    public String getTplField() {
        return tplField;
    }

    public String getTplFieldWithValue() {
        return tplFieldWithValue;
    }

    public String getTplGetter() {
        return tplGetter;
    }

    public String getTplImport() {
        return tplImport;
    }

    public String getTplSetter() {
        return tplSetter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    /**
     * 
     * @param property
     * @param tpl
     *
     * @since 2014. 10. 29.
     */
    public void set(String property, String tpl) {
        if (elementList.contains(property)) {
            setElement(property, tpl);

            return;
        }

        if (attributeList.contains(property)) {
            setAttribute(property, tpl);

            return;
        }
    }

    /**
     * 
     * @param attribute
     *            One of followings.<br>
     *            <ul>
     *            <li>fieldImport
     *            <li>objectImport
     *            <li>nullType
     *            </ul>
     * @param value
     *
     * @since 2014. 10. 29.
     */
    @SuppressWarnings("unchecked")
    private void setAttribute(String attribute, String value) {
        switch (attribute) {
            case ATTR_FIELD_IMPORT:
                try {
                    ((ConcurrentSkipListSet<String>) attributes.get(attribute)).addAll(Arrays.asList(JSONArrayFactory.createStringArray(value)));
                } catch (Exception ignored) {
                }
                break;
            case ATTR_OBJECT_IMPORT:
                try {
                    ((ConcurrentSkipListSet<String>) attributes.get(attribute)).addAll(Arrays.asList(JSONArrayFactory.createStringArray(value)));
                } catch (Exception ignored) {
                }
                break;
            case ATTR_NULL_TYPE:
                attributes.put(attribute, value);
                break;
            case ATTR_NULL_TYPE_VALUE:
                attributes.put(attribute, value);
                break;
            case ATTR_NULL_TYPE_IMPORT:
                try {
                    ((ConcurrentSkipListSet<String>) attributes.get(attribute)).addAll(Arrays.asList(JSONArrayFactory.createStringArray(value)));
                } catch (Exception ignored) {
                }
                break;
            case ATTR_SERIALIZABLE:
                attributes.put(attribute, value);
                break;
            case ATTR_EXTENSION:
                attributes.put(attribute, value);
                break;
            case ATTR_ARRAY_HANDLER:
                attributes.put(attribute, value);
                break;
        }
    }

    /**
     * 
     * @param element
     *            One of followings.<br>
     *            <ul>
     *            <li>class
     *            <li>import
     *            <li>field
     *            <li>field_with_value
     *            <li>setter
     *            <li>getter
     *            </ul>
     * @param tpl
     *
     * @since 2014. 10. 29.
     */
    private void setElement(String element, String tpl) {
        switch (element) {
            case ELEM_CLASS:
                this.tplClass = tpl;
                break;
            case ELEM_IMPORT:
                this.tplImport = tpl;
                break;
            case ELEM_FIELD:
                this.tplField = tpl;
                break;
            case ELEM_FIELD_WITH_VALUE:
                this.tplFieldWithValue = tpl;
                break;
            case ELEM_SETTER:
                this.tplSetter = tpl;
                break;
            case ELEM_GETTER:
                this.tplGetter = tpl;
                break;
        }
    }

    @Override
    public String toString() {
        return ("JavaClassTemplates [category=" + category + ", tplClass=" + tplClass + ", tplImport=" + tplImport + ", tplField=" + tplField + ", tplFieldWithValue="
                + tplFieldWithValue + ", tplSetter=" + tplSetter + ", tplGetter=" + tplGetter + "]").replaceAll("\n", "");
    }

    public NamedTemplate tplClass() {
        return new NamedTemplate(tplClass);
    }

    /**
     * Require following names.<br>
     * <ul>
     * <li>jsonKey: for json field
     * <li>type: for class type
     * <li>var: for field name
     * </ul>
     * 
     * @return
     *
     * @since 2014. 10. 24.
     */
    public NamedTemplate tplField() {
        return new NamedTemplate(tplField);
    }

    /**
     * Require following names.<br>
     * <ul>
     * <li>jsonKey: for json field
     * <li>type: for class type
     * <li>var: for field name
     * <li>value: for field default value
     * </ul>
     * 
     * @return
     *
     * @since 2014. 10. 24.
     */
    public NamedTemplate tplFieldWithValue() {
        return new NamedTemplate(tplFieldWithValue);
    }

    /**
     * Require following names.<br>
     * <ul>
     * <li>type: for a type of a returned value.
     * <li>name: for method name.
     * <li>var: for a value.
     * </ul>
     * 
     * @return
     *
     * @since 2014. 10. 24.
     */
    public NamedTemplate tplGetter() {
        return new NamedTemplate(tplGetter);
    }

    /**
     * Require following names.<br>
     * <ul>
     * <li>qualifier: for import definition
     * </ul>
     * 
     * @return
     *
     * @since 2014. 10. 24.
     */
    public NamedTemplate tplImport() {
        return new NamedTemplate(tplImport);
    }

    /**
     * Require following names.<br>
     * <ul>
     * <li>type: for a type of a returned value.
     * <li>name: for method name.
     * <li>var: for a value.
     * </ul>
     * 
     * @return
     *
     * @since 2014. 10. 24.
     */
    public NamedTemplate tplSetter() {
        return new NamedTemplate(tplSetter);
    }

    static String toJavaField(String str) {
        return StringUtils.isJavaIdentifier(str) ? JavaLanguageKeywords.contains(str) ? "$" + str : str : "$" + str;
    }

    static String toLowerCamelCase(String str) {
        String[] strs = str.split("[_|\\s|\\:|\\-|\\$|\\#|\\@|\\.]");
        if (strs.length == 1) {
            return StringUtils.toLowerCase(str, 0);
        } else {
            return StringUtils.toLowerCamelCase(ArrayUtils.removeAll(strs, ""));
        }
    }

    static String toUpperCamelCase(String str) {
        String[] strs = str.split("[_|\\s|\\:|\\-|\\$|\\#|\\@|\\.]");
        if (strs.length == 1) {
            return StringUtils.toUpperCase(str, 0);
        } else {
            return StringUtils.toUpperCamelCase(ArrayUtils.removeAll(strs, ""));
        }
    }

    static class ArrayHandler extends DefaultJSONModel {

        private static final long serialVersionUID = 1L;

        @JSONField(name = "class")
        private String implClass;

        @JSONField(name = "type")
        private String type;

        public ArrayHandler() {
        }

        /**
         *
         * @param implClass
         *            $class to set.
         *
         * @since 2014. 12. 16.
         */
        public void setImplClass(String implClass) {
            this.implClass = implClass;
        }

        /**
         *
         * @param type
         *            type to set.
         *
         * @since 2014. 12. 16.
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return $class
         *
         * @since 2014. 12. 16.
         */
        public String getImplClass() {
            return this.implClass;
        }

        /**
         *
         * @return type
         *
         * @since 2014. 12. 16.
         */
        public String getType() {
            return this.type;
        }

    }

}

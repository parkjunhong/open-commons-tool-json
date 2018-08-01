/*
 *
 * This file is generated under this project, "iCanvas".
 *
 * Date  : 2014. 10. 24. 오전 11:55:33
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tool.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import open.commons.collection.FIFOMap;
import open.commons.json.util.JSONUtil;
import open.commons.text.NamedTemplate;
import open.commons.utils.ArrayUtils;

import code.org.codehaus.jettison.json.JSONException;
import code.org.codehaus.jettison.json.JSONObject;

public class JavaClassAttrContainer implements Comparable<JavaClassAttrContainer> {

    /**
     * key: json key value#key: json object key
     */
    private static Map<String, Map<String, ClassAttrConfig>> classConfigMap = new ConcurrentSkipListMap<>();
    /** key: json key, value#key: Import List */
    private static Map<String, JavaClassAttrContainer> jcaContainers = new FIFOMap<>();
    /** key: json key, value#key: nested json object key */
    private static Map<String, Map<String, List<ClassAttrConfig>>> classConfigCollisions = new ConcurrentSkipListMap<>();
    /** key: json key, value: json sources */
    private static Map<String, List<JSONObject>> classJsonSources = new ConcurrentSkipListMap<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.");

    private String jsonSource = "";

    private JavaClassTemplate template;

    // private final JavaClassTemplate template;
    private final NamedTemplate classTemplate;
    private String parentClass;

    private String $class;
    private String $serializable = "";
    private Set<String> imports = new ConcurrentSkipListSet<>();
    private Set<String> fields = new ConcurrentSkipListSet<>();
    private Set<String> methods = new ConcurrentSkipListSet<>();

    private ConcurrentSkipListSet<JavaClassAttrContainer> nestedClasses = new ConcurrentSkipListSet<>();

    public JavaClassAttrContainer(JavaClassTemplate template) {
        this.template = template;
        this.classTemplate = new NamedTemplate(template.getTplClass());

        Set<String> objectImports = template.getAttribute(JavaClassTemplate.ATTR_OBJECT_IMPORT);
        for (String objectImport : objectImports) {
            this.addImport(template.createImport(objectImport));
        }

        Set<String> fieldImports = template.getAttribute(JavaClassTemplate.ATTR_FIELD_IMPORT);
        for (String fieldImport : fieldImports) {
            this.addImport(template.createImport(fieldImport));
        }

        this.$serializable = template.getAttribute(JavaClassTemplate.ATTR_SERIALIZABLE);
    }

    public void addField(String field) {
        this.fields.add(field);
    }

    public void addFields(String... fields) {
        this.fields.addAll(Arrays.asList(fields));
    }

    public void addImport(String $import) {
        this.imports.add($import);
    }

    public void addImports(Collection<String> imports) {
        this.imports.addAll(imports);
    }

    public void addImports(String... imports) {
        this.imports.addAll(Arrays.asList(imports));
    }

    private void addJsonSource(String jsonSource, StringBuffer sb, String indent) {
        String[] javaDocStrs = ArrayUtils.removeAll(jsonSource.split("[\n]"), "");

        if (javaDocStrs.length < 1) {
            return;
        }

        for (String str : javaDocStrs) {
            sb.append("\n" + indent + "  * " + str);
        }
    }

    public void addMethod(String method) {
        this.methods.add(method);
    }

    public void addMethods(String... methods) {
        this.methods.addAll(Arrays.asList(methods));
    }

    public void addNestedClass(JavaClassAttrContainer nestedClass) {
        this.nestedClasses.add(nestedClass);
    }

    @Override
    public int compareTo(JavaClassAttrContainer o) {
        return this.$class.compareTo(o.$class);
    }

    public String eval(String indent, boolean displayJavadoc) {

        classTemplate.addValue("indent", indent);

        StringBuffer sb = new StringBuffer();

        // start - Imports : 2014. 10. 31. 오전 11:37:18
        if (isRoot()) {

            for (JavaClassAttrContainer nestedClass : nestedClasses) {
                for (String $import : nestedClass.getImports()) {
                    imports.add($import);
                }
            }

            for (String $import : imports) {
                sb.append(indent);
                sb.append($import);
                sb.append('\n');
            }

        }
        classTemplate.addValue("imports", sb.toString());
        // end - Imports : 2014. 10. 31. 오전 11:37:18

        sb.setLength(0);
        if (displayJavadoc) {
            List<JSONObject> jsonSources = classJsonSources.get($class);
            Iterator<JSONObject> itr = jsonSources.iterator();
            String jsonSource = null;

            try {
                if (itr.hasNext() && (jsonSource = itr.next().toString(2)) != null) {
                    int count = 0;

                    String date = dateFormat.format(new Date(System.currentTimeMillis()));

                    append(sb//
                            , indent, "/**"//
                            , '\n', indent, "  * <a href=\"http://tools.ietf.org/html/rfc7159\">JSON</a> source: <br>"//
                            , '\n', indent, "  * <pre>"//
                            , '\n', indent, "  * "//
                            , '\n', indent, "  * [CASE - ", count, ']'//
                            , '\n', indent, "  * ");

                    addJsonSource(JSONUtil.toString(jsonSource, 2), sb, indent);

                    while (itr.hasNext() && (jsonSource = itr.next().toString(2)) != null) {
                        append(sb//
                                , '\n', indent, "  * "//
                                , '\n', indent, "  * "//
                                , '\n', indent, "  * [CASE - ", ++count, ']'//
                                , '\n', indent, "  * ");

                        addJsonSource(JSONUtil.toString(jsonSource, 2), sb, indent);
                    }

                    append(sb//
                            , '\n', indent, "  * </pre>"//
                            , '\n', indent, "  *"//
                            , '\n', indent, "  * @since ", date//
                            , '\n', indent, " */");

                }
            } catch (JSONException ignored) {
            }
        }

        classTemplate.addValue("java-doc", sb.toString());

        classTemplate.addValue("static", !isRoot() ? "static" : "");
        classTemplate.addValue("class", $class);

        classTemplate.addValue("serializable", indent + "\t" + $serializable);

        // start - Fields : 2014. 10. 31. 오전 11:37:09
        sb.setLength(0);
        sb = new StringBuffer();
        for (String field : fields) {
            append(sb//
                    , '\n'//
                    , indent, '\t'// 들여쓰기
                    , field.replaceAll("\n", "\n" + indent + "\t"), '\n');
        }
        classTemplate.addValue("fields", sb.toString());
        // end - Fields : 2014. 10. 31. 오전 11:37:09

        // start - Methods : 2014. 10. 31. 오전 11:37:04
        sb.setLength(0);
        sb = new StringBuffer();
        for (String method : methods) {
            append(sb//
                    , '\n'//
                    , indent, '\t'// 들여쓰기
                    , method.replaceAll("\n", "\n" + indent + "\t"), "\n");
        }
        classTemplate.addValue("methods", sb.toString());
        // end - Methods : 2014. 10. 31. 오전 11:37:04

        // start - Nested Classes : 2014. 10. 31. 오전 11:36:52
        sb.setLength(0);
        for (JavaClassAttrContainer nestedClass : nestedClasses) {
            append(sb//
                    , indent, '\t'//
                    , nestedClass.eval(indent + "\t", displayJavadoc), '\n');
        }
        classTemplate.addValue("nestedclass", sb.toString());
        // end - Nested Classes : 2014. 10. 31. 오전 11:36:52

        return classTemplate.format();
    }

    public String getClassName() {
        return this.$class;
    }

    public Collection<String> getImports() {
        return this.imports;
    }

    public String getJsonSource() {
        return this.jsonSource;
    }

    public String getParentClass() {
        return this.parentClass;
    }

    public boolean isRoot() {
        return this.parentClass == null;
    }

    public void setClass(String $class) {
        this.$class = $class;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public void setJsonSource(String jsonSource) {
        this.jsonSource = jsonSource;
    }

    public void setMethods(Set<String> methods) {
        this.methods = methods;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public static void addClassJsonSource(String className, JSONObject newJsonObj) {
        List<JSONObject> jsonSources = classJsonSources.get(className);

        boolean isNewJsonObj = true;
        if (jsonSources == null) {
            jsonSources = new ArrayList<>();

            classJsonSources.put(className, jsonSources);
        } else {
            // check unique
            for (JSONObject jsonObj : jsonSources) {
                if (compareJsonObjects(newJsonObj, jsonObj)) {
                    isNewJsonObj = false;
                    break;
                }
            }
        }

        if (isNewJsonObj) {
            jsonSources.add(newJsonObj);
        }
    }

    public static void addJavaClassAttrContainer(String className, JavaClassAttrContainer javaClassAttrContainer) {

        JavaClassAttrContainer container = jcaContainers.get(className);

        if (container != null) {
            container.addImports(javaClassAttrContainer.getImports());
        } else {
            jcaContainers.put(className, javaClassAttrContainer);
        }
    }

    private static void append(StringBuffer sb, Object... objects) {
        for (Object obj : objects) {
            sb.append(obj);
        }
    }

    /**
     * @param className
     *            JSON object's key
     * @param classAttrConfigs
     * @param template
     *            TODO
     * @since 2014. 10. 24.
     */
    public static void applyClassAttrConfigs(String className, Map<String, ClassAttrConfig> classAttrConfigs, JavaClassTemplate template) {
        Map<String, ClassAttrConfig> legacy = classConfigMap.get(className);

        if (legacy == null) {
            classConfigMap.put(className, legacy = new HashMap<>());
        }

        String jsonKey = null;
        ClassAttrConfig newConfig = null;
        ClassAttrConfig oldConfig = null;
        List<ClassAttrConfig> collisionContents = null;
        for (Entry<String, ClassAttrConfig> classAttrConfig : classAttrConfigs.entrySet()) {
            jsonKey = classAttrConfig.getKey();
            newConfig = classAttrConfig.getValue();

            if ((oldConfig = legacy.get(jsonKey)) != null) {

                if (!oldConfig.equals(newConfig)) {
                    String nullType = template.getAttribute(JavaClassTemplate.ATTR_NULL_TYPE);

                    if (newConfig.type.contains(nullType)) {
                        continue;
                    }

                    Map<String, List<ClassAttrConfig>> collisions = classConfigCollisions.get(className);
                    if (collisions == null) {
                        collisions = new HashMap<>();

                        classConfigCollisions.put(className, collisions);
                    }

                    collisionContents = collisions.get(jsonKey);
                    if (collisionContents == null) {
                        collisionContents = new ArrayList<>();

                        collisions.put(jsonKey, collisionContents);
                    }

                    try {
                        ClassAttrConfig clone = (ClassAttrConfig) oldConfig.clone();

                        clone.field = clone.field.replaceAll("\n", " ");
                        clone.getter = clone.getter.replaceAll("\n", " ");
                        clone.setter = clone.setter.replaceAll("\n", " ");

                        collisionContents.add(clone);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }

            legacy.put(jsonKey, newConfig);
        }
    }

    @SuppressWarnings("unchecked")
    private static boolean compareJsonObjects(JSONObject jsonObj1, JSONObject jsonObj2) {

        boolean compared = true;

        Iterator<String> keys = jsonObj1.keys();

        if (!keys.hasNext() && jsonObj2.keys().hasNext()) {
            return false;
        }

        String key = null;
        Object obj1 = null;
        Object obj2 = null;
        while (keys.hasNext() && compared) {
            try {
                obj1 = jsonObj1.get(key = keys.next());

                if (!jsonObj2.has(key)) {
                    compared = false;
                } else {
                    obj2 = jsonObj2.get(key);

                    if (!obj1.getClass().equals(obj2.getClass())) {
                        compared = false;
                    }

                    if (JSONObject.class.isAssignableFrom(obj1.getClass())) {
                        compared = compareJsonObjects((JSONObject) obj1, (JSONObject) obj2);
                    }
                }
            } catch (Exception e) {
                compared = false;
            }
        }

        return compared;
    }

    public static FIFOMap<String, JavaClassAttrContainer> evaluate(boolean childrenIsNested) {

        FIFOMap<String, JavaClassAttrContainer> classes = new FIFOMap<>();

        String className = null;
        JavaClassAttrContainer jcaContainer = null;
        String parentClassName = null;
        JavaClassAttrContainer parentJca = null;

        for (Entry<String, JavaClassAttrContainer> entry : JavaClassAttrContainer.jcaContainers.entrySet()) {
            className = entry.getKey();
            jcaContainer = entry.getValue();

            if (!JavaClassAttrContainer.classConfigMap.containsKey(className)) {
                continue;
            }

            for (ClassAttrConfig classAttrConfig : JavaClassAttrContainer.classConfigMap.get(className).values()) {
                jcaContainer.addField(classAttrConfig.field);
                jcaContainer.addMethods(classAttrConfig.getter, classAttrConfig.setter);
            }

            if (childrenIsNested) {
                parentJca = jcaContainer;
                while (!parentJca.isRoot()) {
                    parentClassName = parentJca.getParentClass();
                    parentJca = jcaContainers.get(parentClassName);
                }
            } else {
                jcaContainer.setParentClass(null);
            }

            if (parentClassName != null) {
                parentJca.addNestedClass(jcaContainer);

                parentClassName = null;
                parentJca = null;
            } else {
                classes.put(className, jcaContainer);
            }
        }

        return classes;
    }

    public static void flush() {
        jcaContainers.clear();
        classConfigMap.clear();
        classConfigCollisions = new ConcurrentSkipListMap<>();
        classJsonSources.clear();
    }

    public static Map<String, Map<String, List<ClassAttrConfig>>> getClassConfigCollisions() {
        return classConfigCollisions;
    }
}

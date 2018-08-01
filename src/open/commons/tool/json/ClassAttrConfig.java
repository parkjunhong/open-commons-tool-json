/*
 *
 * This file is generated under this project, "open-commons-tool-json".
 *
 * Date  : 2014. 10. 31. 오전 10:38:13
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tool.json;

public class ClassAttrConfig {

    public String key;
    public String type;
    public String field;
    public String getter;
    public String setter;

    public ClassAttrConfig() {
        this.key = "";
        this.type = "";
        this.field = "";
        this.getter = "";
        this.setter = "";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ClassAttrConfig clone = new ClassAttrConfig();

        clone.key = this.key;
        clone.type = type;
        clone.field = this.field;
        clone.getter = this.getter;
        clone.setter = this.setter;

        return clone;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClassAttrConfig other = (ClassAttrConfig) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (getter == null) {
            if (other.getter != null)
                return false;
        } else if (!getter.equals(other.getter))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (setter == null) {
            if (other.setter != null)
                return false;
        } else if (!setter.equals(other.setter))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((getter == null) ? 0 : getter.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((setter == null) ? 0 : setter.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ClassConfig [key=" + key + ", type=" + type + ", field=" + field + ", getter=" + getter + ", setter=" + setter + "]";
    }
}

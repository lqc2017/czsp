package czsp.authority.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("PERMISSION_OBJECT")
public class PermissionObject {
	@Column(value = "O_ID")
	@Name
	private String objectId;

	@Column(value = "OBJECT_TYPE")
	private String objectType;

	@Column(value = "OBJECT_NAME")
	private String objectName;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}

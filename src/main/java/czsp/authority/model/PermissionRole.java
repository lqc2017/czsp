package czsp.authority.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("PERMISSION_ROLE")
@PK({ "roleId", "objectId" })
public class PermissionRole {
	@Column(value = "ROLE_ID")
	private String roleId;

	@Column(value = "OBJECT_ID")
	private String objectId;

	public PermissionRole(String roleId, String objectId) {
		this.setObjectId(objectId);
		this.setRoleId(roleId);
	}

	public PermissionRole() {
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}

package czsp.authority.model;

import java.util.Date;

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

	@Column(value = "URL")
	private String url;

	@Column(value = "CREATE_TIME")
	private Date createTime;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

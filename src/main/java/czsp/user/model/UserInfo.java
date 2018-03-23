package czsp.user.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("USER_INFO")
public class UserInfo {
	@Name
	@Column(value = "USER_ID")
	private String userId;

	@Column(value = "DEPARTMENT_ID")
	private String departmentId;

	@Column(value = "NAME")
	private String name;

	@Column(value = "ROLE_ID")
	private String roleId;

	@Column(value = "QX_ID")
	private String qxId;

	@Column(value = "PHONE_NUMBER")
	private String phoneNumber;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getQxId() {
		return qxId;
	}

	public void setQxId(String qxId) {
		this.qxId = qxId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

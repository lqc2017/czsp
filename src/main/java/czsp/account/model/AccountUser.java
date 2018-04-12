package czsp.account.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("ACCOUNT_USER")
@PK({ "userName", "userId" })
public class AccountUser {
	@Column(value = "USER_NAME")
	private String userName;

	@Column(value = "USER_ID")
	private String userId;

	@Column(value = "CREATE_TIME")
	private Date createTime;

	@Column(value = "IS_DEFAULT")
	private String isDefault;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}

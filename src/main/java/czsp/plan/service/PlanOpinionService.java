package czsp.plan.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.plan.dao.PlanOpinionDao;
import czsp.plan.model.PlanOpinion;
import czsp.user.dao.UserOperationDao;
import czsp.user.model.UserOperation;

@IocBean
public class PlanOpinionService {
	@Inject
	private PlanOpinionDao planOpinionDao;

	@Inject
	private UserOperationDao userOperationDao;

	/**
	 * 全琛 2018年4月2日 新增办理意见
	 */
	public void addOpinion(PlanOpinion planOpinion) {
		planOpinion.setCreateTime(new Date());
		planOpinionDao.addOpinion(planOpinion);

		UserOperation operation = new UserOperation("签收", new Date(), planOpinion.getCreateBy(),
				planOpinion.getNodeId(), planOpinion.getInstanceId(), planOpinion.getInstanceId());
		userOperationDao.addOperation(operation);
	}

	/**
	 * 全琛 2018年4月2日 根据当前实例id和当前节点id筛选已填办理意见
	 */
	public PlanOpinion getLatestOpinion(String instanceId, String curNode, String curUserId, String opType) {
		return planOpinionDao.getLatestOpinion(instanceId, curNode,curUserId, opType);
	}

	/**
	 * 全琛 2018年4月2日 更新办理意见
	 */
	public void updateOpinion(PlanOpinion planOpinion) {
		planOpinion.setUpdateTime(new Date());
		planOpinionDao.updateOpinion(planOpinion);
	}

}

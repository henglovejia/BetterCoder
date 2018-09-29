package club.bettercoder.questions.po;

import java.util.List;

public class DbQuestion  {

	private int questionId;

	private String questionUuid;
	
	private String questionNo;

	private String subject;

	private String phase;

	private String grade;

	private int status;

	private int baseTypeId;
	
	private String typeValue;

	private int parentQuestionId;

	private String parentQuestionUuid;

	private int innerOrder;

	private int hasChild;

	private int difficulty;

	private String category;

	private int year;

	private int useTimes;
	
	private int accuracy;

	private String province;

	private String city;

	private String source;

	private String remark;

	private String creator;

	private int createUserId;
	
	private String createUserUuid;

	private String createTime;

	private int auditUserId;
	
	private String auditUserUuid;

	private String auditTime;

	private int modifyUserId;

	private String modifyTime;

	private QuestionContent qContent;

	private List<QuestionItem> itemList;
	
	private String createUserWorkLocation;

	String childJson = "";
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionUuid() {
		return questionUuid;
	}

	public void setQuestionUuid(String questionUuid) {
		this.questionUuid = questionUuid;
	}
	
	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBaseTypeId() {
		return baseTypeId;
	}

	public void setBaseTypeId(int baseTypeId) {
		this.baseTypeId = baseTypeId;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public int getParentQuestionId() {
		return parentQuestionId;
	}

	public void setParentQuestionId(int parentQuestionId) {
		this.parentQuestionId = parentQuestionId;
	}

	public String getParentQuestionUuid() {
		return parentQuestionUuid;
	}

	public void setParentQuestionUuid(String parentQuestionUuid) {
		this.parentQuestionUuid = parentQuestionUuid;
	}

	public int getInnerOrder() {
		return innerOrder;
	}

	public void setInnerOrder(int innerOrder) {
		this.innerOrder = innerOrder;
	}

	public int getHasChild() {
		return hasChild;
	}

	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(int auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public int getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(int modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public QuestionContent getqContent() {
		return qContent;
	}

	public void setqContent(QuestionContent qContent) {
		this.qContent = qContent;
	}

	public List<QuestionItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<QuestionItem> itemList) {
		this.itemList = itemList;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getCreateUserWorkLocation() {
		return createUserWorkLocation;
	}

	public void setCreateUserWorkLocation(String createUserWorkLocation) {
		this.createUserWorkLocation = createUserWorkLocation;
	}

	public String getCreateUserUuid() {
		return createUserUuid;
	}

	public void setCreateUserUuid(String createUserUuid) {
		this.createUserUuid = createUserUuid;
	}

	public String getAuditUserUuid() {
		return auditUserUuid;
	}

	public void setAuditUserUuid(String auditUserUuid) {
		this.auditUserUuid = auditUserUuid;
	}

	public String getChildJson() {
		return childJson;
	}

	public void setChildJson(String childJson) {
		this.childJson = childJson;
	}
	
	
}

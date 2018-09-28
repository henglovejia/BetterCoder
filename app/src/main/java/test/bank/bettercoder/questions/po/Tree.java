package test.bank.bettercoder.questions.po;

public class Tree{
	private int id;
	private String knowledgeUuid;
	private String resourceUuid;
	private String parent;
	private String text;
	private int topic;
	private String subject;
	private String version;
	private String grade;
	private String term;
	private String phase;
	private String area;
	private String remark;
	private int level;
	private String examQuestions;//questionuuid串，school用
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKnowledgeUuid() {
		return knowledgeUuid;
	}

	public void setKnowledgeUuid(String knowledgeUuid) {
		this.knowledgeUuid = knowledgeUuid;
	}

	public String getResourceUuid() {
		return resourceUuid;
	}

	public void setResourceUuid(String resourceUuid) {
		this.resourceUuid = resourceUuid;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getExamQuestions() {
		return examQuestions;
	}

	public void setExamQuestions(String examQuestions) {
		this.examQuestions = examQuestions;
	}

	
}

package club.bettercoder.questions.po;

import java.io.Serializable;

public class QuestionItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int itemId;

	private int questionId;

	private int innerOrder;

	private int rightFlag;

	private int status;

	private String content;

	private String htmlContent;
	
	private String questionUuid;
	
	private String option;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getInnerOrder() {
		return innerOrder;
	}

	public void setInnerOrder(int innerOrder) {
		this.innerOrder = innerOrder;
	}

	public int getRightFlag() {
		return rightFlag;
	}

	public void setRightFlag(int rightFlag) {
		this.rightFlag = rightFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getQuestionUuid() {
		return questionUuid;
	}

	public void setQuestionUuid(String questionUuid) {
		this.questionUuid = questionUuid;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
}

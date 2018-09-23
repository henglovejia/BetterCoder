package test.bank.bettercoder.questions.model;

import java.io.Serializable;

public class QuestionContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int questionId;

	private String analyse;

	private String htmlAnalyse;

	private String reply;

	private String htmlReply;

	private String appraise;

	private String htmlAppraise;

	private String answer;

	private String htmlAnswer;

	private String content;

	private String htmlContent;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnalyse() {
		return analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}

	public String getHtmlAnalyse() {
		return htmlAnalyse;
	}

	public void setHtmlAnalyse(String htmlAnalyse) {
		this.htmlAnalyse = htmlAnalyse;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getHtmlReply() {
		return htmlReply;
	}

	public void setHtmlReply(String htmlReply) {
		this.htmlReply = htmlReply;
	}

	public String getAppraise() {
		return appraise;
	}

	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}

	public String getHtmlAppraise() {
		return htmlAppraise;
	}

	public void setHtmlAppraise(String htmlAppraise) {
		this.htmlAppraise = htmlAppraise;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getHtmlAnswer() {
		return htmlAnswer;
	}

	public void setHtmlAnswer(String htmlAnswer) {
		this.htmlAnswer = htmlAnswer;
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

}

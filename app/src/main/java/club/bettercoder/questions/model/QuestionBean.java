package club.bettercoder.questions.model;

public class QuestionBean {
    public String knowledgeUuid,subject;

    public QuestionBean() {

    }

    public QuestionBean(String knowledgeUuid,String subject) {
        this.knowledgeUuid = knowledgeUuid;
        this.subject = subject;
    }
}

package club.bettercoder.questions.model;

import java.util.List;

import club.bettercoder.base.BcBaseModel;
import club.bettercoder.questions.po.DbQuestion;

public class QuestionModel extends BcBaseModel {
    public List<DbQuestion> questions;
    public Integer totalNum;
    public Integer number;
    public Integer historyId;
}

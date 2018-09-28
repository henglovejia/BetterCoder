package test.bank.bettercoder.questions.model;

import java.util.List;

import test.bank.bettercoder.base.BcBaseModel;
import test.bank.bettercoder.questions.po.DbQuestion;

public class QuestionModel extends BcBaseModel {
    public List<DbQuestion> questions;
    public Integer totalNum;
    public Integer number;
    public Integer historyId;
}

package club.bettercoder.questions.model;

import java.util.List;

import club.bettercoder.base.BaseModel;
import club.bettercoder.questions.po.DbQuestion;

public class QuestionModel extends BaseModel {
    public List<DbQuestion> questions;
    public Integer number;
    public Integer historyId;
}

package test.bank.bettercoder.questions.po;

import test.bank.bettercoder.base.BcBaseBean;
import test.bank.bettercoder.base.BcBaseModel;

/**
 * 学科类
 * @author Tom
 *
 */
public class Subject extends BcBaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3589336263062732997L;
	private int id;
	private int innerOrder;
	private String name;
	private int status;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInnerOrder() {
		return innerOrder;
	}

	public void setInnerOrder(int innerOrder) {
		this.innerOrder = innerOrder;
	}
	
	
}

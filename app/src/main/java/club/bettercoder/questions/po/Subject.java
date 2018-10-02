package club.bettercoder.questions.po;

/**
 * 学科类
 * @author Tom
 *
 */
public class Subject {

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

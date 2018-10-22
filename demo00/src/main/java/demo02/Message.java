package demo02;

public class Message {

	private int id;
	private String name;
	private String message;

	public Message() {
		super();
	}

	public Message(int id, String name, String message) {
		super();
		this.id = id;
		this.name = name;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

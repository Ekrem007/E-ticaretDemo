package shopApp.shopApp.core.utilities.results ;

public class Result {
	private boolean success;
	private String message;
	private Object data;
	
	public Result (boolean success) {
		this.success = success;
		
	}
	public Result (boolean success, String message) {
		this(success);
		this.message = message;
		
	}
	public Result(boolean success, String message, Object data) {
        this(success, message);
        this.data = data;
    }
	public boolean isSuccess() {
		return this.success;
	}
	public String getMessage() {
		return this.message;
	}

}

package cn.huahai.ftp.bean;

public class ResponseResult<T> {
	private Integer state;
	private String message;
	private  T data;
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void address(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ResponseResult(Integer state, String message, T data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}
	public ResponseResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", massage=" + message + ", data=" + data + "]";
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

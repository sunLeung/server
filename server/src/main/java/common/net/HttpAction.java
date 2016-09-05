package common.net;


public abstract class HttpAction implements Cloneable{
	
	public abstract String excute(HttpPacket packet);
	
	public String handle(HttpPacket packet){
		return excute(packet);
	}
	
	public HttpAction clone() {
		try {
			return (HttpAction) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}

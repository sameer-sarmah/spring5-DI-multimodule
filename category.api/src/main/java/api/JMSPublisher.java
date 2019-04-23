package api;

public interface JMSPublisher<T> {
	void publish(T subject,String topic);
}

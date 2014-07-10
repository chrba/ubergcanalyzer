package de.morten.model.task;

class Task {

	private MessageConsumer data;
	private Task next;
	
	public Task(MessageConsumer data)
	{
		this.data = data;
		this.next = null;
	}
	
	public void add(MessageConsumer data)
	{
		final Task task = new Task(data);
		final Task lastNode = getLastNode();
		lastNode.next = task;
	}
	
	private Task getLastNode() {
		Task t = this;
		
		while(t.next != null)
			t = t.next;
		
		return t;
	}

	public void moveToEnd() {
		if(next == null)
			return;
		
		final Task lastNode = getLastNode();
		lastNode.next = this;
	}
	
	public void resetTail()
	{
		Task t = this.next;
		while(t != null) {
			t.data.reset();
			t = t.next;
		}
	}
	
	public boolean consume(final Message message) {
		Task t = this;
		boolean consumed = false;
		while(t != null) {
			consumed = t.data.consume(message);
			if(consumed) {
				t.resetTail();
				t.moveToEnd();
				break;
			}
			t = t.next;
		}
		return consumed;
	}
}

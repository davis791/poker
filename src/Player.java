

public class Player {
	int position = 0;
	int positionBigBlind = 0;
	int stack = 0;
	public Player(int stack, int startPosition) {
		this.stack = stack;
		this.position = startPosition;
		this.positionBigBlind = this.position - 2;
	}

}

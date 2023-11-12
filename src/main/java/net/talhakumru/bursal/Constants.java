package net.talhakumru.bursal;

public class Constants {
	public static final String API_KEY = "qfnXIirS9uRgcHHwNc6yLoiNuSpBFZcD4UKSs1zo";
	public static final String REQUEST_URL = "https://bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app/applications.json";
	
	public enum State{
		UNDECIDED("Karar verilmedi."),
		APPROVED("Onaylandı."),
		DENIED("Reddedildi.")
		;
		
		private final String label;
		
		private State(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
}

package net.talhakumru.bursal;

public class Constants {
	public static final String API_KEY = "qfnXIirS9uRgcHHwNc6yLoiNuSpBFZcD4UKSs1zo";
	public static final String REQUEST_URL = "https://bursal-8bbc3-default-rtdb.europe-west1.firebasedatabase.app/applications.json";
	
	public enum State{
		UNDECIDED("Karar verilmedi.", "#ffad33"),
		APPROVED("Onaylandı.", "#00cc66"),
		DENIED("Reddedildi.", "#e60000")
		;
		
		private final String label;
		private final String color;
		
		private State(String label, String color) {
			this.label = label;
			this.color = color;
		}

		public String getLabel() {
			return label;
		}

		public String getColor() {
			return color;
		}
	}
}

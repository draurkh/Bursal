package net.talhakumru.bursal;

public class Constants {
	// directory of uploaded cv files
	public static final String CV_DIR = System.getProperty("user.home") + System.getProperty("file.separator") + "cv_files";
	public static final String MONGO_CLUSTER_PASSWD = "Kue5kYqwpOrGkcE9";
	
	// approval state of scholarship application
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

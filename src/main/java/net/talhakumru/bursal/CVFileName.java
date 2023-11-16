package net.talhakumru.bursal;

// File name conventions for CV files
public class CVFileName {
	private static long id = 1; // global counter

	public String get(String firstName, String lastName, String extension) {
		return String.format("(%d)_%s-%s.%s", id++, firstName, lastName, extension);
	}
}

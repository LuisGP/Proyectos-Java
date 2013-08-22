package interfaz;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MP3FileFilter extends FileFilter {
	public boolean accept(File file) {
		return file.isDirectory() || file.getAbsolutePath().endsWith(".mp3");
	}

	public String getDescription() {
		return "MP3 file (*.mp3)";
	}
}

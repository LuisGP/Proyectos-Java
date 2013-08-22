package gestarchivos;

import java.io.File;


import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v2.ID3V2Tag;

public class Archivo {
	private String ruta;
	private String titulo;
	private String autor;
	private String album;
	private String genero;
	private int anyo;

	
	public Archivo(String ruta){
		File oSourceFile = new File(ruta);
        MP3File oMP3File = new MP3File(oSourceFile);
        this.ruta = ruta;
        
        try {
			ID3V2Tag tag = oMP3File.getID3V2Tag();
			album = tag.getAlbum();
			titulo = tag.getTitle();
			autor = tag.getArtist();
			genero = tag.getGenre();
			anyo = tag.getYear();
		} catch (ID3Exception e) {
			try {
				ID3V1Tag tag = oMP3File.getID3V1Tag();
				album = tag.getAlbum();
				titulo = tag.getTitle();
				autor = tag.getArtist();
				genero = tag.getGenre().toString();
				anyo = new Integer(tag.getYear());
			} catch (ID3Exception e1){
				this.titulo = ruta;
			}
		}
	}
	
	public Archivo(String ruta, String titulo, String autor, String album, String genero, int anyo) {
		this.ruta = ruta;
		this.titulo = titulo;
		this.autor = autor;
		this.album = album;
		this.genero = genero;
		this.anyo = anyo;
	}

	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
		if (this.existe()) {
			File oSourceFile = new File(ruta);
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				tag.setAlbum(album);
				oMP3File.setID3Tag(tag);
				oMP3File.sync();
			} catch (ID3Exception e) {
				try {
					ID3V1Tag tag = oMP3File.getID3V1Tag();
					tag.setAlbum(album);
					oMP3File.setID3Tag(tag);
					oMP3File.sync();
				} catch (ID3Exception e1){}
			}
		}
	}
	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
		if (this.existe()) {
			File oSourceFile = new File(ruta);
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				tag.setYear(anyo);
				oMP3File.setID3Tag(tag);
				oMP3File.sync();
			} catch (ID3Exception e) {
				try {
					ID3V1Tag tag = oMP3File.getID3V1Tag();
					tag.setYear(String.valueOf(anyo));
					oMP3File.setID3Tag(tag);
					oMP3File.sync();
				} catch (ID3Exception e1){}
			}
		}
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
		if (this.existe()) {
			File oSourceFile = new File(ruta);
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				tag.setArtist(autor);
				oMP3File.setID3Tag(tag);
				oMP3File.sync();
			} catch (ID3Exception e) {
				try {
					ID3V1Tag tag = oMP3File.getID3V1Tag();
					tag.setArtist(autor);
					oMP3File.setID3Tag(tag);
					oMP3File.sync();
				} catch (ID3Exception e1){}
			}
		}
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
		if (this.existe()) {
			File oSourceFile = new File(ruta);
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				tag.setGenre(genero);
				oMP3File.setID3Tag(tag);
				oMP3File.sync();
			} catch (ID3Exception e) {	}
		}
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
		if (this.existe()) {
			File oSourceFile = new File(ruta);
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				tag.setTitle(titulo);
				oMP3File.setID3Tag(tag);
				oMP3File.sync();
			} catch (ID3Exception e) {
				try {
					ID3V1Tag tag = oMP3File.getID3V1Tag();
					tag.setTitle(titulo);
					oMP3File.setID3Tag(tag);
					oMP3File.sync();
				} catch (ID3Exception e1){}
			}
		}
	}
	
	public String toString() {
		String s = this.autor+" - "+this.titulo;
		return s;
	}
	
	public boolean existe() {
		File f = new File(this.ruta);
		return f.exists();
	}
	
	public Archivo copiar(){
		return new Archivo(this.ruta, this.titulo, this.autor, this.album, this.genero, this.anyo);
	}
	

}

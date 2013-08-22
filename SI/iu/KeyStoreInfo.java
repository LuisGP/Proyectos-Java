package iu;

public interface KeyStoreInfo {
	public void setStorePass(char[] sp);
	public void mostrar();
	public void setCertificados(String[] certificados);
	public String getKeystorePath();
}
